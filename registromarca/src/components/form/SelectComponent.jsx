import React from 'react';
import { ChevronDown } from 'lucide-react';

function SelectComponent({
  label,
  options = [],
  valueKey = 'id',
  labelKey = 'nombre',
  error,
  placeholder = 'Selecciona una opción',
  id,
  name,
  disabled = false,
  className = '',
  ...props
}) {
  return (
    <div className="w-full">
      {label && (
        <label htmlFor={id || name} className="block text-xs font-semibold text-neutral-700 uppercase tracking-wider mb-1.5">
          {label}
        </label>
      )}
      <div className="relative">
        <select
          id={id || name}
          name={name}
          disabled={disabled}
          className={`w-full appearance-none px-3.5 py-2.5 rounded-lg border text-sm transition-all focus:outline-none focus:ring-2 bg-white pr-9 cursor-pointer disabled:bg-neutral-100 disabled:cursor-not-allowed ${
            error
              ? 'border-red-500 focus:ring-red-400 bg-red-50/40 text-red-950'
              : 'border-neutral-300 focus:ring-neutral-900 text-neutral-900'
          } ${className}`}
          {...props}
        >
          <option value="">{placeholder}</option>
          {options.map((opt, idx) => {
            const val = typeof opt === 'object' && opt !== null ? opt[valueKey] : opt;
            const text = typeof opt === 'object' && opt !== null ? opt[labelKey] : opt;
            return (
              <option key={val ?? idx} value={val}>
                {text}
              </option>
            );
          })}
        </select>
        <ChevronDown size={16} className="absolute right-3 top-1/2 -translate-y-1/2 text-neutral-500 pointer-events-none" />
      </div>
      {error && <p className="mt-1 text-xs text-red-600 font-medium animate-fadeIn">{error}</p>}
    </div>
  );
}

export default SelectComponent;