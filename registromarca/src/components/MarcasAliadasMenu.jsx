import React from 'react';
import americanEagleLogo from '../assets/marcasAliadas/american-eagle.svg';
import americaninoLogo from '../assets/marcasAliadas/americanino.svg';
import chevignonLogo from '../assets/marcasAliadas/chevignon.svg';
import espritLogo from '../assets/marcasAliadas/esprit.svg';
import nafnafLogo from '../assets/marcasAliadas/nafnaf.svg';
import rifleLogo from '../assets/marcasAliadas/rifle.svg';

export const MARCAS_LISTA = [
  { name: 'American Eagle', logo: americanEagleLogo, href: '#american-eagle' },
  { name: 'Americanino', logo: americaninoLogo, href: '#americanino' },
  { name: 'Chevignon', logo: chevignonLogo, href: '#chevignon' },
  { name: 'Esprit', logo: espritLogo, href: '#esprit' },
  { name: 'NAF NAF', logo: nafnafLogo, href: '#nafnaf' },
  { name: 'Rifle', logo: rifleLogo, href: '#rifle' }
];

function MarcasAliadasMenu({ onItemClick }) {
  return (
    <div className="max-w-6xl mx-auto py-8 px-8">
      <div className="border-b border-neutral-200 pb-3 mb-6">
        <h3 className="text-sm font-bold uppercase tracking-wider text-neutral-900 flex items-center gap-2">
          <span>Marcas Aliadas Oficiales</span>
          <span className="text-[10px] font-semibold text-neutral-500 bg-neutral-100 px-2.5 py-0.5 rounded-full lowercase">
            {MARCAS_LISTA.length} marcas
          </span>
        </h3>
        <p className="text-xs text-neutral-500 mt-1">
          Regístrate para acumular puntos en todas las compras de nuestras marcas asociadas.
        </p>
      </div>

      <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-6 gap-4">
        {MARCAS_LISTA.map((marca) => (
          <a
            key={marca.name}
            href={marca.href}
            onClick={onItemClick}
            className="group bg-neutral-50 hover:bg-white border border-neutral-200 hover:border-neutral-900 rounded-xl p-4 flex flex-col items-center justify-center transition-all duration-200 shadow-2xs hover:shadow-md"
          >
            <div className="h-14 w-full flex items-center justify-center p-1.5">
              {marca.logo ? (
                <img
                  src={marca.logo}
                  alt={marca.name}
                  className="max-h-full max-w-full object-contain transition-transform duration-200 group-hover:scale-105"
                />
              ) : (
                <div className="w-10 h-10 rounded-lg bg-neutral-200 flex items-center justify-center font-bold text-xs text-neutral-700">
                  {marca.name.substring(0, 2).toUpperCase()}
                </div>
              )}
            </div>
            <span className="text-[11px] font-bold tracking-wider uppercase text-neutral-700 group-hover:text-black text-center mt-1">
              {marca.name}
            </span>
          </a>
        ))}
      </div>
    </div>
  );
}

export default MarcasAliadasMenu;