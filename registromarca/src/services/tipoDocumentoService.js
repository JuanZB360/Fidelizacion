import api from './apiBack';

export const tipoDocumentoService = {
  listar: () => api.get('/tipo-documento'),
  buscarPorId: (id) => api.get(`/tipo-documento/${id}`),
};