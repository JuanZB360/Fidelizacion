import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { X, Loader2, CheckCircle2 } from 'lucide-react';
import InputComponent from '../components/form/InputComponenet';
import SelectComponent from '../components/form/SelectComponent';
import { usuarioService } from '../services/usuarioService';
import { tipoDocumentoService } from '../services/tipoDocumentoService';
import { marcaService } from '../services/marcaService';
import { ubicacionService } from '../services/ubicacionService';

export default function RegistroPage() {
  const [tiposDoc, setTiposDoc] = useState([]);
  const [marcas, setMarcas] = useState([]);
  const [paises, setPaises] = useState([]);
  const [departamentos, setDepartamentos] = useState([]);
  const [ciudades, setCiudades] = useState([]);

  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);

  const [fieldErrors, setFieldErrors] = useState({});
  const [generalError, setGeneralError] = useState('');

  // Estructura exacta requerida por el backend
  const [formData, setFormData] = useState({
    nombre: '',
    apellido: '',
    email: '',
    contrasena: '',
    rol: 'CLIENTE',
    tipoDocumento: {
      id: ''
    },
    numeroDocumento: '',
    fechaNacimiento: '',
    marca: {
      id: ''
    },
    direccion: {
      direccion: '',
      ciudad: '',
      departamento: '',
      pais: ''
    }
  });

  // Bloqueo del scroll del fondo (body)
  useEffect(() => {
    const scrollBarCompensation = window.innerWidth - document.documentElement.clientWidth;
    const previousOverflow = document.body.style.overflow;
    const previousPaddingRight = document.body.style.paddingRight;

    document.body.style.overflow = 'hidden';
    if (scrollBarCompensation > 0) {
      document.body.style.paddingRight = `${scrollBarCompensation}px`;
    }

    return () => {
      document.body.style.overflow = previousOverflow;
      document.body.style.paddingRight = previousPaddingRight;
    };
  }, []);

  useEffect(() => {
    const cargarCatalogos = async () => {
      try {
        const [resDoc, resMarcas, listaPaises] = await Promise.all([
          tipoDocumentoService.listar(),
          marcaService.listar(),
          ubicacionService.obtenerPaises()
        ]);
        setTiposDoc(resDoc.data || []);
        setMarcas(resMarcas.data || []);
        setPaises(listaPaises || []);
        console.log('Respuesta Backend TiposDoc:', resDoc.data);
        console.log('Respuesta Backend Marcas:', resMarcas.data);
      } catch (err) {
        console.error('Error cargando catálogos:', err);
        setGeneralError('No se pudieron cargar los datos de configuración del sistema.');
      }
    };
    cargarCatalogos();
  }, []);

  useEffect(() => {
    if (!formData.direccion.pais) {
      setDepartamentos([]);
      setCiudades([]);
      return;
    }
    const cargarDepartamentos = async () => {
      const deps = await ubicacionService.obtenerDepartamentos(formData.direccion.pais);
      setDepartamentos(deps || []);
      setCiudades([]);
    };
    cargarDepartamentos();
  }, [formData.direccion.pais]);

  useEffect(() => {
    if (!formData.direccion.departamento) {
      setCiudades([]);
      return;
    }
    const cargarCiudades = async () => {
      const ciuds = await ubicacionService.obtenerCiudades(
        formData.direccion.pais,
        formData.direccion.departamento
      );
      setCiudades(ciuds || []);
    };
    cargarCiudades();
  }, [formData.direccion.departamento, formData.direccion.pais]);

  // Manejador para campos raíz
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));

    if (fieldErrors[name]) {
      setFieldErrors((prev) => {
        const updated = { ...prev };
        delete updated[name];
        return updated;
      });
    }
  };

  // Manejador para objetos relacionales con id (tipoDocumento, marca)
  const handleRelationChange = (e, relationName) => {
    const value = e.target.value;
    setFormData((prev) => ({
      ...prev,
      [relationName]: { id: value || '' }
    }));

    // Limpia el error si existía para la relación
    if (fieldErrors[relationName] || fieldErrors[`${relationName}.id`]) {
      setFieldErrors((prev) => {
        const updated = { ...prev };
        delete updated[relationName];
        delete updated[`${relationName}.id`];
        return updated;
      });
    }
  };

  // Manejador para dirección
  const handleDireccionChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => {
      const nuevaDireccion = { ...prev.direccion, [name]: value };
      // Si cambia el país, resetear departamento y ciudad
      if (name === 'pais') {
        nuevaDireccion.departamento = '';
        nuevaDireccion.ciudad = '';
      }
      // Si cambia el departamento, resetear ciudad
      if (name === 'departamento') {
        nuevaDireccion.ciudad = '';
      }
      return {
        ...prev,
        direccion: nuevaDireccion
      };
    });

    const keySimple = name;
    const keyAnidada = `direccion.${name}`;

    if (fieldErrors[keySimple] || fieldErrors[keyAnidada]) {
      setFieldErrors((prev) => {
        const updated = { ...prev };
        delete updated[keySimple];
        delete updated[keyAnidada];
        return updated;
      });
    }
  };

  // Manejador específico para fecha de nacimiento (formato dd/mm/aaaa)
  const handleFechaChange = (e) => {
    let val = e.target.value;
    // Solo permitir números y barras
    val = val.replace(/[^0-9/]/g, '');

    // Auto-formato de barras si el usuario escribe los dígitos seguidos
    const digitsOnly = val.replace(/\//g, '');
    if (!val.includes('/') && digitsOnly.length > 2) {
      if (digitsOnly.length <= 4) {
        val = `${digitsOnly.slice(0, 2)}/${digitsOnly.slice(2)}`;
      } else {
        val = `${digitsOnly.slice(0, 2)}/${digitsOnly.slice(2, 4)}/${digitsOnly.slice(4, 8)}`;
      }
    }

    if (val.length <= 10) {
      setFormData((prev) => ({ ...prev, fechaNacimiento: val }));
    }

    if (fieldErrors.fechaNacimiento) {
      setFieldErrors((prev) => {
        const updated = { ...prev };
        delete updated.fechaNacimiento;
        return updated;
      });
    }
  };

  // Validador estricto para fecha de nacimiento (dd/mm/aaaa)
  const validarFechaNacimiento = (valor) => {
    if (!valor || !valor.trim()) {
      return 'La fecha de nacimiento es obligatoria';
    }

    const regexFormato = /^(\d{2})\/(\d{2})\/(\d{4})$/;
    const match = valor.trim().match(regexFormato);

    if (!match) {
      return 'El formato debe ser dd/mm/aaaa (ej. 15/06/1995)';
    }

    const dia = parseInt(match[1], 10);
    const mes = parseInt(match[2], 10);
    const anio = parseInt(match[3], 10);

    if (mes < 1 || mes > 12) {
      return 'El mes ingresado no es válido (debe estar entre 01 y 12)';
    }

    const fechaObj = new Date(anio, mes - 1, dia);
    if (
      fechaObj.getFullYear() !== anio ||
      fechaObj.getMonth() !== mes - 1 ||
      fechaObj.getDate() !== dia
    ) {
      return 'La fecha ingresada no corresponde a un día de calendario válido';
    }

    const hoy = new Date();
    if (fechaObj > hoy) {
      return 'La fecha de nacimiento no puede ser futura';
    }

    if (anio < 1900) {
      return 'El año ingresado no es válido';
    }

    // Validar mayoría de edad (mínimo 18 años)
    let edad = hoy.getFullYear() - anio;
    const mesDiff = hoy.getMonth() - (mes - 1);
    const diaDiff = hoy.getDate() - dia;
    if (mesDiff < 0 || (mesDiff === 0 && diaDiff < 0)) {
      edad--;
    }

    if (edad < 18) {
      return 'Debes tener al menos 18 años y proporcionar una fecha válida';
    }

    return null;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setGeneralError('');
    setFieldErrors({});

    // 1. Validación de fecha de nacimiento (dd/mm/aaaa)
    const errorFecha = validarFechaNacimiento(formData.fechaNacimiento);
    if (errorFecha) {
      setFieldErrors((prev) => ({ ...prev, fechaNacimiento: errorFecha }));
      setGeneralError('Corrige los errores señalados en el formulario.');
      return; // No pasa la petición si no cumple el formato
    }

    setLoading(true);

    // 2. Convertir dd/mm/aaaa a yyyy-mm-dd (ISO) para el backend
    const [dia, mes, anio] = formData.fechaNacimiento.trim().split('/');
    const fechaBackend = `${anio}-${mes}-${dia}`;

    // El payload utiliza directamente la estructura requerida por el backend (UUIDs como string y fecha ISO)
    const payload = {
      ...formData,
      tipoDocumento: { id: formData.tipoDocumento.id },
      marca: { id: formData.marca.id },
      fechaNacimiento: fechaBackend
    };

    try {
      await usuarioService.crear(payload);
      setSuccess(true);
      setTimeout(() => {
        window.history.back();
      }, 1800);
    } catch (err) {
      console.error('Error en el registro:', err);
      console.error('Error completo:', err);
      console.log('STATUS:', err.response?.status);
      console.log('RESPUESTA EXACTA DEL BACKEND:', err.response?.data);
      console.log('PAYLOAD QUE SE INTENTÓ ENVIAR:', payload);
      
      const data = err.response?.data;
      

      if (data) {
        if (data.errores && typeof data.errores === 'object') {
          const mappedErrors = {};

          Object.entries(data.errores).forEach(([campo, mensaje]) => {
            let fieldKey = campo;

            // Normalización para los inputs
            if (fieldKey === 'marca' || fieldKey.startsWith('marca.')) {
              fieldKey = 'marca';
            } else if (fieldKey === 'tipoDocumento' || fieldKey.startsWith('tipoDocumento.')) {
              fieldKey = 'tipoDocumento';
            }

            mappedErrors[fieldKey] = mensaje;
          });

          setFieldErrors(mappedErrors);
          setGeneralError(data.mensaje || 'Corrige los errores señalados en el formulario.');
        } else if (data.campo) {
          let fieldKey = data.campo;
          if (fieldKey.startsWith('marca')) fieldKey = 'marca';
          if (fieldKey.startsWith('tipoDocumento')) fieldKey = 'tipoDocumento';
          setFieldErrors({ [fieldKey]: data.mensaje });
          setGeneralError(data.mensaje);
        } else if (data.mensaje) {
          setGeneralError(data.mensaje);
        } else {
          setGeneralError('Ocurrió un error inesperado al registrar el usuario.');
        }
      } else {
        setGeneralError('No hubo respuesta del servidor. Revisa tu conexión.');
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 sm:p-6 overflow-hidden">
      {/* Fondo oscuro traslúcido */}
      <Link
        to={-1}
        aria-label="Cerrar modal al hacer clic afuera"
        className="fixed inset-0 bg-black/60 backdrop-blur-xs transition-opacity cursor-default"
      />

      {/* Tarjeta del modal */}
      <div className="relative w-full max-w-2xl bg-white rounded-2xl shadow-2xl border border-neutral-200 z-10 max-h-[90vh] flex flex-col">
        {/* Cabecera */}
        <div className="flex items-center justify-between px-6 sm:px-8 py-5 border-b border-neutral-100 bg-white rounded-t-2xl z-20">
          <div>
            <h2 className="text-xl font-bold text-neutral-900">Crear cuenta</h2>
            <p className="text-xs text-neutral-500 mt-0.5">Ingresa tus datos para acumular puntos en tus marcas</p>
          </div>

          <Link
            to={-1}
            aria-label="Cerrar modal"
            className="p-1.5 rounded-lg text-neutral-400 hover:text-neutral-700 hover:bg-neutral-100 transition-colors inline-flex items-center justify-center cursor-pointer"
          >
            <X size={20} />
          </Link>
        </div>

        {/* Cuerpo scrolleable con diseño de scroll fino */}
        <div
          className="p-6 sm:p-8 overflow-y-auto flex-1 
          [scrollbar-width:thin] 
          [scrollbar-color:theme(colors.neutral.300)_transparent] 
          [&::-webkit-scrollbar]:w-2 
          [&::-webkit-scrollbar-track]:bg-transparent 
          [&::-webkit-scrollbar-thumb]:bg-neutral-300 
          [&::-webkit-scrollbar-thumb]:rounded-full 
          hover:[&::-webkit-scrollbar-thumb]:bg-neutral-400"
        >
          {generalError && (
            <div className="mb-5 p-3.5 rounded-lg bg-red-50 border border-red-200 text-xs text-red-700 font-medium animate-fadeIn">
              {generalError}
            </div>
          )}

          {success ? (
            <div className="py-12 text-center space-y-3">
              <CheckCircle2 size={48} className="text-emerald-500 mx-auto animate-bounce" />
              <h3 className="text-lg font-bold text-neutral-900">¡Registro Exitoso!</h3>
              <p className="text-xs text-neutral-500">Tu cuenta fue creada correctamente. Redirigiendo...</p>
            </div>
          ) : (
            <form className="space-y-6" onSubmit={handleSubmit} noValidate>
              {/* Datos Personales */}
              <div>
                <h3 className="text-xs font-bold uppercase tracking-wider text-neutral-400 mb-3 border-b pb-1">
                  Datos Personales
                </h3>
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                  <InputComponent
                    label="Nombre"
                    name="nombre"
                    placeholder="Ej. Ana"
                    value={formData.nombre}
                    onChange={handleChange}
                    error={fieldErrors.nombre}
                  />
                  <InputComponent
                    label="Apellido"
                    name="apellido"
                    placeholder="Ej. García"
                    value={formData.apellido}
                    onChange={handleChange}
                    error={fieldErrors.apellido}
                  />
                </div>
              </div>

              {/* Documento y Nacimiento */}
              <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
                <SelectComponent
                  label="Tipo Documento"
                  name="tipoDocumento"
                  placeholder="Seleccionar..."
                  options={tiposDoc}
                  valueKey="id"
                  labelKey="nombre"
                  value={formData.tipoDocumento.id}
                  onChange={(e) => handleRelationChange(e, 'tipoDocumento')}
                  error={fieldErrors.tipoDocumento || fieldErrors['tipoDocumento.id']}
                />
                <InputComponent
                  label="N° Documento"
                  name="numeroDocumento"
                  placeholder="1234567890"
                  value={formData.numeroDocumento}
                  onChange={handleChange}
                  error={fieldErrors.numeroDocumento}
                />
                <InputComponent
                  label="Fecha de Nacimiento"
                  type="text"
                  name="fechaNacimiento"
                  placeholder="dd/mm/aaaa"
                  maxLength={10}
                  value={formData.fechaNacimiento}
                  onChange={handleFechaChange}
                  error={fieldErrors.fechaNacimiento}
                />
              </div>

              {/* Credenciales y Marca */}
              <div>
                <h3 className="text-xs font-bold uppercase tracking-wider text-neutral-400 mb-3 border-b pb-1">
                  Credenciales y Marca
                </h3>
                <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
                  <InputComponent
                    label="Email"
                    type="email"
                    name="email"
                    placeholder="ana@example.com"
                    value={formData.email}
                    onChange={handleChange}
                    error={fieldErrors.email}
                  />
                  <InputComponent
                    label="Contraseña"
                    type="password"
                    name="contrasena"
                    placeholder="••••••••"
                    value={formData.contrasena}
                    onChange={handleChange}
                    error={fieldErrors.contrasena}
                  />
                  <SelectComponent
                    label="Marca Principal"
                    name="marca"
                    placeholder="Seleccionar marca..."
                    options={marcas}
                    valueKey="id"
                    labelKey="nombre"
                    value={formData.marca.id}
                    onChange={(e) => handleRelationChange(e, 'marca')}
                    error={fieldErrors.marca || fieldErrors['marca.id']}
                  />
                </div>
              </div>

              {/* Ubicación */}
              <div>
                <h3 className="text-xs font-bold uppercase tracking-wider text-neutral-400 mb-3 border-b pb-1">
                  Ubicación
                </h3>
                <div className="grid grid-cols-1 sm:grid-cols-3 gap-4 mb-4">
                  <SelectComponent
                    label="País"
                    name="pais"
                    placeholder="Seleccionar país..."
                    options={paises}
                    valueKey="nombre"
                    labelKey="nombre"
                    value={formData.direccion.pais}
                    onChange={handleDireccionChange}
                    error={fieldErrors['direccion.pais'] || fieldErrors.pais}
                  />
                  <SelectComponent
                    label="Departamento / Estado"
                    name="departamento"
                    placeholder="Seleccionar departamento..."
                    options={departamentos}
                    value={formData.direccion.departamento}
                    onChange={handleDireccionChange}
                    disabled={!formData.direccion.pais || departamentos.length === 0}
                    error={fieldErrors['direccion.departamento'] || fieldErrors.departamento}
                  />
                  <SelectComponent
                    label="Ciudad"
                    name="ciudad"
                    placeholder="Seleccionar ciudad..."
                    options={ciudades}
                    value={formData.direccion.ciudad}
                    onChange={handleDireccionChange}
                    disabled={!formData.direccion.departamento || ciudades.length === 0}
                    error={fieldErrors['direccion.ciudad'] || fieldErrors.ciudad}
                  />
                </div>
                <InputComponent
                  label="Dirección de Residencia"
                  name="direccion"
                  placeholder="Ej. Calle 10 # 20-30"
                  value={formData.direccion.direccion}
                  onChange={handleDireccionChange}
                  error={fieldErrors['direccion.direccion'] || fieldErrors.direccion}
                />
              </div>

              <div className="pt-2">
                <button
                  type="submit"
                  disabled={loading}
                  className="w-full py-3 bg-neutral-900 hover:bg-neutral-800 disabled:bg-neutral-400 text-white rounded-xl text-sm font-semibold tracking-wide transition-all shadow-md flex items-center justify-center gap-2 cursor-pointer"
                >
                  {loading ? (
                    <>
                      <Loader2 size={18} className="animate-spin" />
                      <span>Validando registro...</span>
                    </>
                  ) : (
                    <span>Completar Registro</span>
                  )}
                </button>
              </div>
            </form>
          )}
        </div>
      </div>
    </div>
  );
}