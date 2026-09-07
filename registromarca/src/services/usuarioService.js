import api from './apiBack';

export const usuarioService = {
  crear: (datos) => api.post('/usuario', datos),
  listar: () => api.get('/usuario'),
  buscarPorId: (id) => api.get(`/usuario/${id}`),
  actualizar: (id, datos) => api.patch(`/usuario/${id}`, datos),
};