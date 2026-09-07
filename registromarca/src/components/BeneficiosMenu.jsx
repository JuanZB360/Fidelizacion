import React from 'react';
import { Sparkles } from 'lucide-react';
import LinkButton from './LinkButton';
import { useLocation } from 'react-router-dom';

export const BENEFICIOS_ITEMS = [
  'Puntos por Compras',
  'Descuentos Exclusivos',
  'Regalos de Cumpleaños',
  'Experiencias VIP'
];

function BeneficiosMenu({ onItemClick }) {


  const location = useLocation();

  return (
    <div className="max-w-4xl mx-auto py-8 px-8 grid grid-cols-1 md:grid-cols-2 gap-8">
      <div className="space-y-3">
        <h4 className="text-xs uppercase font-bold tracking-wider text-neutral-900 border-b border-neutral-200 pb-1.5">
          Recompensas
        </h4>
        <ul className="space-y-2 text-sm text-neutral-600">
          {BENEFICIOS_ITEMS.map((item, idx) => (
            <li key={idx}>
              <a
                href="#beneficios"
                onClick={onItemClick}
                className="hover:text-black hover:underline transition-colors block py-0.5"
              >
                {item}
              </a>
            </li>
          ))}
        </ul>
      </div>

      <div className="bg-neutral-50 p-5 rounded-lg border border-neutral-200 flex flex-col justify-between">
        <div>
          <span className="text-[10px] uppercase font-bold tracking-widest text-red-600 flex items-center gap-1">
            <Sparkles size={12} /> Destacado
          </span>
          <p className="font-semibold text-neutral-900 mt-1.5 text-sm">Programa Fidelización</p>
          <p className="text-xs text-neutral-500 mt-1">
            Crea tu cuenta y acumula beneficios inmediatos en tus marcas preferidas.
          </p>
        </div>
        <div className="mt-4">
          <LinkButton
            to="/registro"
            state={{ backgroundLocation: location }}
            variant="primary"
            size="sm"
            fullWidth
            onClick={onItemClick}
          >
            Unirme ahora
          </LinkButton>
        </div>
      </div>
    </div>
  );
}

export default BeneficiosMenu;