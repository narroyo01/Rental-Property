import axios from 'axios';

export default {

  add(property) {
    return axios.post('/property', property)
  },
  getById(id) {
    return axios.get('/property/' + id)
  },
  getAvailableProperties() {
    return axios.get('/property/available');
  },
  getProperties() {
    return axios.get('/property/');
  },
  update(property) {
    return axios.put('/property/' + property.propertyId, property)
  }

}
