import React from 'react';
import { Link } from 'react-router-dom';

/**
 * Enlace visualmente estilizado como botón utilizando react-router-dom.
 * 
 * @param {string} to - Ruta destino requerida.
 * @param {string} variant - 'primary' | 'secondary' | 'outline' | 'ghost' | 'danger'
 * @param {string} size - 'sm' | 'md' | 'lg'
 * @param {boolean} fullWidth - Ocupa el 100% del contenedor si es true.
 */
function LinkButton({
  to,
  state,
  children,
  variant = 'primary',
  size = 'md',
  fullWidth = false,
  className = '',
  onClick,
  ...props
}) {
  const baseStyles = 'inline-flex items-center justify-center font-semibold rounded-lg transition-all duration-150 select-none cursor-pointer text-center no-underline focus:outline-none focus:ring-2 focus:ring-offset-2';

  const variants = {
    primary: 'bg-neutral-900 hover:bg-neutral-800 text-white shadow-2xs hover:shadow-sm focus:ring-neutral-900',
    secondary: 'bg-neutral-100 hover:bg-neutral-200 text-neutral-900 focus:ring-neutral-400',
    outline: 'border border-neutral-300 hover:border-neutral-900 text-neutral-800 hover:text-black bg-transparent focus:ring-neutral-900',
    ghost: 'text-neutral-700 hover:text-neutral-900 hover:bg-neutral-100 focus:ring-neutral-300',
    danger: 'bg-red-600 hover:bg-red-700 text-white focus:ring-red-500'
  };

  const sizes = {
    sm: 'text-xs py-1.5 px-3 gap-1.5',
    md: 'text-xs sm:text-sm py-2 px-4 sm:px-5 gap-2',
    lg: 'text-sm sm:text-base py-2.5 px-6 gap-2.5'
  };

  const widthStyle = fullWidth ? 'w-full' : '';
  const combinedClasses = `${baseStyles} ${variants[variant] || variants.primary} ${sizes[size] || sizes.md} ${widthStyle} ${className}`.trim();

  return (
    <Link to={to} onClick={onClick} state={state} className={combinedClasses} {...props}>
      {children}
    </Link>
  );
}

export default LinkButton;