import axios from 'axios';

export default {

  add(property) {
    return axios.post('/add-property', property)
  },

}
