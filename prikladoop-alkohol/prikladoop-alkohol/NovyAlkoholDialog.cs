using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace prikladoop_alkohol
{
    public partial class NovyAlkoholDialog : Form
    {
        public NovyAlkoholDialog()
        {
            InitializeComponent();
        }
        private AlkoholickyNapoj alkoholickyNapoj;

        internal AlkoholickyNapoj AlkoholickyNapoj { get => alkoholickyNapoj; set => alkoholickyNapoj = value; }

        public DialogResult ukaHO(List<string> kategorie)
        {
            comboBox1.Items.AddRange(kategorie.ToArray());
            return (ShowDialog());
        }

        private void button1_Click(object sender, EventArgs e)
        {
            alkoholickyNapoj = new AlkoholickyNapoj(comboBox1.Text, textBox1.Text, float.Parse(textBox2.Text));
        }
    }
}
