import axios from 'axios';

export default {

  add(property) {
    return axios.post('/property', property)
  },
  getById(id) {
    return axios.get('/property/' + id)
  },

  update(property) {
    return axios.put('/property/' + property.propertyId, property)
  }

}
