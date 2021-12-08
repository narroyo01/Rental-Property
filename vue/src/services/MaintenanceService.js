import axios from 'axios';

export default {

  getByUserId(id){
    return axios.get(`/maintenance-request/${id}`)
  },

}
