import axios from 'axios';
import api from './apiBack';

const GEO_API = 'https://countriesnow.space/api/v0.1/countries';

export const ubicacionService = {
  // Endpoints backend
  crear: (datos) => api.post('/ubicacion', datos),
  listar: () => api.get('/ubicacion'),

  // API Geográfica pública para Selects en cascada
  obtenerPaises: async () => {
    const res = await axios.get(`${GEO_API}/iso`);
    return res.data.data.map((c) => ({ nombre: c.name, iso2: c.Iso2 }));
  },
  obtenerDepartamentos: async (pais) => {
    const res = await axios.post(`${GEO_API}/states`, { country: pais });
    return res.data.data.states.map((s) => s.name);
  },
  obtenerCiudades: async (pais, departamento) => {
    const res = await axios.post(`${GEO_API}/state/cities`, {
      country: pais,
      state: departamento,
    });
    return res.data.data;
  },
};