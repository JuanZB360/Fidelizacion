import React from 'react';
import { Gift, BadgePercent, Sparkles, ArrowRight, ShieldCheck } from 'lucide-react';
import LinkButton from '../components/LinkButton';
import fondoHero from '../assets/fondoInicio/pexels-cottonbro-6070177.jpg';
import { useLocation } from 'react-router-dom';

function HomePage() {

  const location = useLocation();

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      {/* Sección Hero con imagen de fondo */}
      <section className="relative text-center py-16 lg:py-24 rounded-2xl border border-neutral-200/80 px-6 mb-12 shadow-lg overflow-hidden">
        <img
          src={fondoHero}
          alt="Fondo Fidelización"
          className="absolute inset-0 w-full h-full object-cover object-center"
        />

        {/* Capa de contraste */}
        <div className="absolute inset-0 bg-gradient-to-t from-neutral-950/85 via-neutral-900/65 to-neutral-950/75 backdrop-brightness-90" />

        {/* Contenido principal */}
        <div className="relative z-10">
          <span className="inline-flex items-center gap-1.5 px-3.5 py-1 text-xs font-semibold uppercase tracking-wider bg-white/15 text-white backdrop-blur-md border border-white/20 rounded-full mb-5 shadow-xs">
            <Sparkles size={13} className="text-amber-400" /> Programa Exclusivo
          </span>

          <h1 className="text-3xl sm:text-4xl lg:text-5xl font-extrabold tracking-tight text-white max-w-3xl mx-auto drop-shadow-sm">
            Tus marcas favoritas te recompensan en cada compra
          </h1>

          <p className="mt-4 text-base sm:text-lg text-neutral-200 max-w-2xl mx-auto font-normal drop-shadow-xs">
            Acumula puntos, sube de nivel y redime beneficios únicos en cientos de tiendas.
          </p>

          <div className="mt-8 flex justify-center items-center">
            <LinkButton
              to="/registro"
              state={{ backgroundLocation: location }}
              size="lg"
              className="bg-white hover:bg-neutral-100 !text-neutral-950 font-bold border border-neutral-200 shadow-md hover:shadow-xl hover:scale-105 transition-all duration-200 gap-2"
            >
              <span>Registrarme gratis</span>
              <ArrowRight size={18} className="text-neutral-950" />
            </LinkButton>
          </div>
        </div>
      </section>

      {/* Tarjetas de Beneficios */}
      <section className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="p-6 bg-white rounded-xl border border-neutral-200 shadow-xs hover:shadow-md transition-shadow">
          <div className="w-10 h-10 rounded-lg bg-neutral-100 flex items-center justify-center text-neutral-900 mb-4">
            <Gift size={20} />
          </div>
          <h2 className="text-lg font-bold text-neutral-900 mb-2">Acumula Puntos</h2>
          <p className="text-sm text-neutral-600">
            Cada compra registrada suma puntos directos a tu saldo para que los uses cuando quieras.
          </p>
        </div>

        <div className="p-6 bg-white rounded-xl border border-neutral-200 shadow-xs hover:shadow-md transition-shadow">
          <div className="w-10 h-10 rounded-lg bg-neutral-100 flex items-center justify-center text-neutral-900 mb-4">
            <BadgePercent size={20} />
          </div>
          <h2 className="text-lg font-bold text-neutral-900 mb-2">Descuentos y Ofertas</h2>
          <p className="text-sm text-neutral-600">
            Accede a precios especiales, promociones de temporada y cupones de ahorro exclusivos en tus compras.
          </p>
        </div>

        <div className="p-6 bg-white rounded-xl border border-neutral-200 shadow-xs hover:shadow-md transition-shadow">
          <div className="w-10 h-10 rounded-lg bg-neutral-100 flex items-center justify-center text-neutral-900 mb-4">
            <ShieldCheck size={20} />
          </div>
          <h2 className="text-lg font-bold text-neutral-900 mb-2">Marcas Verificadas</h2>
          <p className="text-sm text-neutral-600">
            Redime tus puntos de forma segura y transparente con las mejores marcas aliadas del país.
          </p>
        </div>
      </section>
    </div>
  );
}

export default HomePage;