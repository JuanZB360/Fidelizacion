import React from 'react';

function InputComponent({ label, error, id, name, className = '', ...props }) {
  return (
    <div className="w-full">
      {label && (
        <label htmlFor={id || name} className="block text-xs font-semibold text-neutral-700 uppercase tracking-wider mb-1.5">
          {label}
        </label>
      )}
      <input
        id={id || name}
        name={name}
        className={`w-full px-3.5 py-2.5 rounded-lg border text-sm transition-all focus:outline-none focus:ring-2 ${
          error
            ? 'border-red-500 focus:ring-red-400 bg-red-50/40 text-red-950 placeholder-red-300'
            : 'border-neutral-300 focus:ring-neutral-900 bg-white text-neutral-900'
        } ${className}`}
        {...props}
      />
      {error && <p className="mt-1 text-xs text-red-600 font-medium animate-fadeIn">{error}</p>}
    </div>
  );
}

export default InputComponent;