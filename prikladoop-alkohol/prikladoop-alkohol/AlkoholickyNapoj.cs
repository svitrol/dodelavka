using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace prikladoop_alkohol
{
    class AlkoholickyNapoj
    {
        public string typ { get; set; }
        public string jmeno { get; set; }
        public float procentniObsahAlkoholu { get; set; }

        public AlkoholickyNapoj(string typ, string jmeno, float procentniObsahAlkoholu)
        {
            this.typ = typ;
            this.jmeno = jmeno;
            this.procentniObsahAlkoholu = procentniObsahAlkoholu;
        }
        public AlkoholickyNapoj(string celek)
        {
            string[] pole = celek.Split(':');
            this.typ = pole[0];
            this.jmeno = pole[1];
            this.procentniObsahAlkoholu = float.Parse(pole[2]);
        }
        public override string ToString()
        {
            return typ + ":" + jmeno + ":" + procentniObsahAlkoholu;
        }
    }
}
