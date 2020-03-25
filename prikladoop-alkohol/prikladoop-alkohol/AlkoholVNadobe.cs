using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace prikladoop_alkohol
{
    class AlkoholVNadobe :AlkoholickyNapoj
    { 
        private int kolikHoTamJe;

        public AlkoholVNadobe(string typ, string jmeno, float procentniObsahAlkoholu,int kolikHoTamJe):base(typ,jmeno,procentniObsahAlkoholu)
        {
            KolikHoTamJe = kolikHoTamJe;
        }
        public AlkoholVNadobe(AlkoholickyNapoj napoj, int kolikHoTamJe) : base(napoj.typ, napoj.jmeno, napoj.procentniObsahAlkoholu)
        {
            KolikHoTamJe = kolikHoTamJe;
        }

        public double HmotnostEtanolu()
        {
            return (kolikHoTamJe * procentniObsahAlkoholu * 0.8) / 100;
        }
        public int KolikHoTamJe { get => kolikHoTamJe; set => kolikHoTamJe = value; }
    }
}
