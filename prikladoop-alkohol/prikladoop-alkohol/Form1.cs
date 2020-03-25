using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;

namespace prikladoop_alkohol
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            posledniVytvoreny = flowLayoutPanel;
        }
        List<AlkoholickyNapoj> zCehoMaNaVyber = new List<AlkoholickyNapoj>();
        List<string> kategorieNapoju = new List<string>();
        void projdiSiSoubor()
        {
            FileStream jed = new FileStream("rejstrik.txt", FileMode.OpenOrCreate, FileAccess.Read);
            StreamReader prochazedlo = new StreamReader(jed);
            while (!prochazedlo.EndOfStream)
            {
                string radek = prochazedlo.ReadLine();
                AlkoholickyNapoj neco = new AlkoholickyNapoj(radek);
                zCehoMaNaVyber.Add(neco);
                if (!kategorieNapoju.Contains(neco.typ)) kategorieNapoju.Add(neco.typ);
            }
            prochazedlo.Close();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Osoba Pepan = new Osoba(textBox2.Text, radioButton1.Checked, int.Parse(textBox3.Text), int.Parse(textBox4.Text));

            foreach(Control neco in Controls)
            {
                if(neco is FlowLayoutPanel)
                {
                    FlowLayoutPanel panel = neco as FlowLayoutPanel;
                    ComboBox typ = ((ComboBox)panel.Controls[0]);
                    ComboBox alkohol= ((ComboBox)panel.Controls[2]);
                    TextBox objemos= ((TextBox)panel.Controls[4]);
                    Pepan.Vypij(new AlkoholVNadobe(new AlkoholickyNapoj(alkohol.Text),int.Parse(objemos.Text)));

                }
            }

            MessageBox.Show("Kdyby se všechen alkohol vstřebal naráz tak by jsi měl v krvi: "+Pepan.promile()+" a potřeboval by jsi "+Pepan.jakdlouhoNezSeToVsakne()+" hodin nez se to vsákne");

        }
        FlowLayoutPanel posledniVytvoreny;
        void nahodTamKomponenty(FlowLayoutPanel cil)
        {
            Label alkoholTyp = new Label();
            alkoholTyp.Text = "typ alkoholu";
            alkoholTyp.AutoSize = true;
            Label vyreobek = new Label();
            vyreobek.Text = "Výrobek";
            vyreobek.AutoSize = true;
            Label objem = new Label();
            objem.Text = "vypitý objem v ml";
            objem.AutoSize = true;
            ComboBox prvnityp = new ComboBox();
            prvnityp.FormattingEnabled = true;
            prvnityp.Name = "kategorie";
            prvnityp.SelectedIndexChanged += new EventHandler(kategorie_SelectedIndexChanged);
            prvnityp.Items.AddRange(kategorieNapoju.ToArray());
            ComboBox druhyvyrobek = new ComboBox();
            druhyvyrobek.Enabled = false;
            druhyvyrobek.Name = "napoje";
            druhyvyrobek.FormattingEnabled = true;
            TextBox objemos = new TextBox();
            objemos.Name = "textBox1";

            cil.Controls.Add(prvnityp);
            cil.Controls.Add(alkoholTyp);
            cil.Controls.Add(druhyvyrobek);
            cil.Controls.Add(vyreobek);
            cil.Controls.Add(objemos);
            cil.Controls.Add(objem);
            
        }

        private void button3_Click(object sender, EventArgs e)
        {
            FlowLayoutPanel dlasiPole = new FlowLayoutPanel();
            dlasiPole.Name = "flowLayoutPanel";
            dlasiPole.Size = new Size(204, 89);
            dlasiPole.Location = new Point(posledniVytvoreny.Location.X + posledniVytvoreny.Size.Width, posledniVytvoreny.Location.Y);
            Controls.Add(dlasiPole);
            Size = new Size(Size.Width + posledniVytvoreny.Width, Size.Height);
            nahodTamKomponenty(dlasiPole);

            dlasiPole.ResumeLayout(false);
            dlasiPole.PerformLayout();

            posledniVytvoreny = dlasiPole;

        }

        private void button2_Click(object sender, EventArgs e)
        {
            NovyAlkoholDialog novinak = new NovyAlkoholDialog();
            if (novinak.ukaHO(kategorieNapoju) == DialogResult.OK)
            {
                AlkoholickyNapoj novy = novinak.AlkoholickyNapoj;
                zCehoMaNaVyber.Add(novy);
                FileStream jed = new FileStream("rejstrik.txt", FileMode.Append, FileAccess.Write);
                StreamWriter zapisnik = new StreamWriter(jed);
                zapisnik.WriteLine(novy.ToString());
                zapisnik.Close();
                MessageBox.Show("Byl zapsán nový: " + novy.ToString());

                if (!kategorieNapoju.Contains(novy.typ))
                {
                    kategorieNapoju.Add(novy.typ);
                    foreach(Control neco in Controls)
                    {
                        if (neco.Name == "flowLayoutPanel")
                        {
                            FlowLayoutPanel panel = neco as FlowLayoutPanel;
                            ComboBox typ = ((ComboBox)panel.Controls[0]);
                            typ.Items.Add(novy.typ);

                        }
                    }
                }
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            projdiSiSoubor();
            kategorie.Items.AddRange(kategorieNapoju.ToArray());
        }

        private void kategorie_SelectedIndexChanged(object sender, EventArgs e)
        {
            ComboBox aktualka = (ComboBox)sender;
            AlkoholickyNapoj[] danaKategorie = zCehoMaNaVyber.FindAll(x => x.typ == aktualka.Text).ToArray();
            foreach(Control napojovy in aktualka.Parent.Controls)
            {
                if(napojovy.Name== "napoje")
                {
                    ((ComboBox)napojovy).Items.AddRange(danaKategorie);
                    ((ComboBox)napojovy).Enabled = true;
                    break;
                }
            }
        }
    }
}
