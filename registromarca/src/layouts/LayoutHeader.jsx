import React, { useState } from 'react';
import { Outlet, Link, useLocation } from 'react-router-dom';
import { ChevronDown, Menu } from 'lucide-react';
import LinkButton from '../components/LinkButton';
import TopBar from '../components/header/TopBar';
import MarcasAliadasMenu from '../components/MarcasAliadasMenu';
import BeneficiosMenu from '../components/BeneficiosMenu';
import MobileDrawer from '../components/header/MobileDrawer';

function LayoutHeader({ children }) {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const [activeSubmenuMobile, setActiveSubmenuMobile] = useState(null);
  const [hoveredMenu, setHoveredMenu] = useState(null);
  const location = useLocation();

  const toggleMobileSubmenu = (id) => {
    setActiveSubmenuMobile((prev) => (prev === id ? null : id));
  };

  return (
    <div className="min-h-screen flex flex-col bg-neutral-50 text-neutral-900">
      <header className="w-full font-sans border-b border-neutral-200 bg-white sticky top-0 z-50">
        
        {/* 1. Barra superior */}
        <TopBar />

        {/* 2. Barra de navegación principal */}
        <div className="max-w-7xl mx-auto px-4 lg:px-8 py-3.5 lg:py-4 flex items-center justify-between gap-4">
          <div className="flex items-center lg:hidden">
            <button
              type="button"
              onClick={() => setMobileMenuOpen(true)}
              aria-label="Abrir menú"
              className="p-1.5 rounded-lg border border-neutral-200 text-neutral-700 hover:bg-neutral-100 transition-colors cursor-pointer"
            >
              <Menu size={20} />
            </button>
          </div>

          <div className="flex-shrink-0 text-center lg:text-left">
            <Link
              to="/inicio"
              className="inline-block tracking-widest text-xl sm:text-2xl lg:text-3xl font-bold uppercase text-neutral-900 hover:opacity-90 transition-opacity"
            >
              FIDELIZACIÓN
            </Link>
          </div>

          <div className="flex items-center">
            <LinkButton to="/registro" state={{ backgroundLocation: location }} variant="primary" size="md">
              Registrarme
            </LinkButton>
          </div>
        </div>

        {/* 3. Menú horizontal de escritorio */}
        <nav className="hidden lg:block border-t border-neutral-100">
          <div className="max-w-7xl mx-auto px-8 flex justify-center">
            <ul className="flex items-center space-x-6">
              {/* Botón de Inicio con LinkButton apuntando a /inicio */}
              <li className="py-2.5">
                <LinkButton
                  to="/inicio"
                  variant="ghost"
                  size="sm"
                  className="!text-[13px] tracking-wider font-semibold text-neutral-800 hover:text-black"
                >
                  Inicio
                </LinkButton>
              </li>

              {/* Menú desplegable Beneficios */}
              <li
                className="group py-3"
                onMouseEnter={() => setHoveredMenu('beneficios')}
                onMouseLeave={() => setHoveredMenu(null)}
              >
                <button
                  type="button"
                  className="text-[13px] tracking-wider font-semibold text-neutral-800 hover:text-black transition-colors flex items-center gap-1.5 cursor-pointer"
                >
                  Beneficios
                  <ChevronDown
                    size={14}
                    className="opacity-70 group-hover:rotate-180 transition-transform duration-200"
                  />
                </button>
              </li>

              {/* Menú desplegable Marcas Aliadas */}
              <li
                className="group py-3"
                onMouseEnter={() => setHoveredMenu('marcas')}
                onMouseLeave={() => setHoveredMenu(null)}
              >
                <button
                  type="button"
                  className="text-[13px] tracking-wider font-semibold text-neutral-800 hover:text-black transition-colors flex items-center gap-1.5 cursor-pointer"
                >
                  Marcas Aliadas
                  <ChevronDown
                    size={14}
                    className="opacity-70 group-hover:rotate-180 transition-transform duration-200"
                  />
                </button>
              </li>
            </ul>
          </div>

          {/* Render condicional de Mega Menú */}
          {hoveredMenu && (
            <div
              className="absolute left-0 w-full bg-white border-b border-neutral-200 shadow-xl transition-all duration-150 animate-fadeIn z-40"
              onMouseEnter={() => setHoveredMenu(hoveredMenu)}
              onMouseLeave={() => setHoveredMenu(null)}
            >
              {hoveredMenu === 'beneficios' && (
                <BeneficiosMenu onItemClick={() => setHoveredMenu(null)} />
              )}
              {hoveredMenu === 'marcas' && (
                <MarcasAliadasMenu onItemClick={() => setHoveredMenu(null)} />
              )}
            </div>
          )}
        </nav>

        {/* 4. Drawer móvil extraído */}
        <MobileDrawer
          isOpen={mobileMenuOpen}
          onClose={() => setMobileMenuOpen(false)}
          activeSubmenu={activeSubmenuMobile}
          onToggleSubmenu={toggleMobileSubmenu}
        />
      </header>

      {/* 5. Área de contenido */}
      <main className="flex-1 w-full">
        {children || <Outlet />}
      </main>
    </div>
  );
}

export default LayoutHeader;