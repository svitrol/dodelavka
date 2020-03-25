using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace prikladoop_alkohol
{
    class Osoba
    {
        private string jmeno;
        private bool jsiMuz;
        private int vek;
        private int vaha;
        private List<AlkoholVNadobe> nadrz = new List<AlkoholVNadobe>();

        public Osoba(string jmeno, bool jsiMuz, int vek, int vaha)
        {
            this.jmeno = jmeno;
            this.jsiMuz = jsiMuz;
            this.vek = vek;
            this.vaha = vaha;
        }
        public double PodilVodyVOrganismu()
        {
            if (jsiMuz) return 0.7;
            return 0.6;
        }
        public double BetaFaktor()//Pokles hladiny alkoholu v krvi za jednu hodinu
        {
            if (jsiMuz) return 0.1;
            return 0.085;
        }
        public void Vypij(AlkoholVNadobe drink)
        {
            nadrz.Add(drink);
        }
        private double hmotnostEtanoluVkrvi()
        {
            double hmotnost = 0;
            foreach(AlkoholVNadobe sklenka in nadrz){
                hmotnost += sklenka.HmotnostEtanolu();
            }
            return hmotnost;

        }
        public double promile()
        {
            return hmotnostEtanoluVkrvi() / (vaha * PodilVodyVOrganismu());
        }

        public double jakdlouhoNezSeToVsakne()
        {
            return hmotnostEtanoluVkrvi() / (vaha * BetaFaktor());
        }

        public string Jmeno { get => jmeno; set => jmeno = value; }
        public bool JsiMuz { get => jsiMuz; set => jsiMuz = value; }
        public int Vek { get => vek; set => vek = value; }
        public int Vaha { get => vaha; set => vaha = value; }

        
    }
}
