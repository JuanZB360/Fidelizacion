import React from 'react';
import { X, ChevronDown } from 'lucide-react';
import LinkButton from '../LinkButton';
import { MARCAS_LISTA } from '../MarcasAliadasMenu';
import { BENEFICIOS_ITEMS } from '../BeneficiosMenu';

function MobileDrawer({
  isOpen,
  onClose,
  activeSubmenu,
  onToggleSubmenu
}) {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex lg:hidden">
      <div
        className="fixed inset-0 bg-black/40 backdrop-blur-sm transition-opacity"
        onClick={onClose}
      />

      <div className="relative w-4/5 max-w-xs bg-white h-full shadow-2xl flex flex-col overflow-y-auto z-10">
        <div className="p-4 border-b border-neutral-200 flex items-center justify-between">
          <span className="font-bold tracking-wider uppercase text-neutral-900">Menú</span>
          <button
            type="button"
            onClick={onClose}
            aria-label="Cerrar menú"
            className="p-1.5 rounded-lg border border-neutral-200 text-neutral-700 hover:bg-neutral-100 transition-colors cursor-pointer"
          >
            <X size={18} />
          </button>
        </div>

        <nav className="flex-1 px-4 py-2">
          <ul className="divide-y divide-neutral-100">
            {/* Inicio con LinkButton */}
            <li className="py-2.5">
              <LinkButton
                to="/inicio"
                variant="ghost"
                fullWidth
                size="sm"
                className="justify-start !px-0 !py-1 text-sm font-semibold tracking-wide text-neutral-800 hover:bg-transparent"
                onClick={onClose}
              >
                Inicio
              </LinkButton>
            </li>

            {/* Acordeón Beneficios */}
            <li className="py-2.5">
              <button
                type="button"
                onClick={() => onToggleSubmenu('beneficios')}
                className="w-full flex items-center justify-between text-sm font-semibold tracking-wide py-1 text-left text-neutral-800 cursor-pointer"
              >
                <span>Beneficios</span>
                <ChevronDown
                  size={14}
                  className={`transition-transform duration-200 ${
                    activeSubmenu === 'beneficios' ? 'rotate-180' : ''
                  }`}
                />
              </button>

              {activeSubmenu === 'beneficios' && (
                <div className="pl-2 mt-2 space-y-1 bg-neutral-50 p-3 rounded-lg">
                  <span className="text-[11px] font-bold text-neutral-400 uppercase tracking-wider block mb-1">
                    Recompensas
                  </span>
                  {BENEFICIOS_ITEMS.map((item, idx) => (
                    <a
                      key={idx}
                      href="#beneficios"
                      onClick={onClose}
                      className="block text-xs py-1 text-neutral-700 hover:text-black"
                    >
                      {item}
                    </a>
                  ))}
                </div>
              )}
            </li>

            {/* Acordeón Marcas Aliadas */}
            <li className="py-2.5">
              <button
                type="button"
                onClick={() => onToggleSubmenu('marcas')}
                className="w-full flex items-center justify-between text-sm font-semibold tracking-wide py-1 text-left text-neutral-800 cursor-pointer"
              >
                <span>Marcas Aliadas</span>
                <ChevronDown
                  size={14}
                  className={`transition-transform duration-200 ${
                    activeSubmenu === 'marcas' ? 'rotate-180' : ''
                  }`}
                />
              </button>

              {activeSubmenu === 'marcas' && (
                <div className="pl-2 mt-2 space-y-2.5 bg-neutral-50 p-3 rounded-lg">
                  <span className="text-[11px] font-bold text-neutral-500 uppercase tracking-wider block">
                    Marcas Aliadas ({MARCAS_LISTA.length})
                  </span>
                  <div className="grid grid-cols-2 gap-2.5">
                    {MARCAS_LISTA.map((marca) => (
                      <a
                        key={marca.name}
                        href={marca.href}
                        onClick={onClose}
                        className="bg-white border border-neutral-200 hover:border-neutral-900 rounded-lg p-2.5 flex flex-col items-center justify-center transition-colors shadow-2xs"
                      >
                        <div className="h-8 w-full flex items-center justify-center mb-1">
                          {marca.logo ? (
                            <img
                              src={marca.logo}
                              alt={marca.name}
                              className="max-h-full max-w-full object-contain"
                            />
                          ) : (
                            <div className="w-7 h-7 rounded bg-neutral-100 flex items-center justify-center text-[10px] font-bold text-neutral-700">
                              {marca.name.substring(0, 2).toUpperCase()}
                            </div>
                          )}
                        </div>
                        <span className="text-[10px] font-semibold text-neutral-700 tracking-wide text-center uppercase">
                          {marca.name}
                        </span>
                      </a>
                    ))}
                  </div>
                </div>
              )}
            </li>
          </ul>
        </nav>

        <div className="p-4 border-t border-neutral-200 bg-neutral-50">
          <LinkButton
            to="/registro"
            variant="primary"
            size="sm"
            fullWidth
            onClick={onClose}
          >
            Registrarme
          </LinkButton>
        </div>
      </div>
    </div>
  );
}

export default MobileDrawer;