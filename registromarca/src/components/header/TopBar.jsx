import React from 'react';
import { Gift, Sparkles, HelpCircle } from 'lucide-react';

function TopBar() {
  return (
    <div className="bg-neutral-900 text-white text-[11px] font-medium tracking-wider uppercase py-2 px-4">
      <div className="max-w-7xl mx-auto flex justify-between items-center">
        <div className="hidden md:flex items-center gap-4 text-neutral-300">
          <span className="flex items-center gap-1.5">
            <Gift size={13} className="text-amber-400" /> Club de Beneficios
          </span>
          <span className="flex items-center gap-1.5">
            <Sparkles size={13} className="text-amber-400" /> Acumula Puntos
          </span>
        </div>

        <div className="w-full md:w-auto text-center font-normal">
          Gana puntos en cada compra y redímelos en tus{' '}
          <span className="font-semibold text-white">marcas favoritas</span>
        </div>
        <div></div>
      </div>
    </div>
  );
}

export default TopBar;