import api from './apiBack';

export const marcaService = {
  listar: async () => api.get('/marca'),
  buscarPorId: async (id) => api.get(`/marca/${id}`),
};