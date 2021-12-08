import axios from 'axios';

export default {

  getByUserId(id){
    return axios.get(`/maintenance-request/${id}`)
  },

  markAsCompleteByMaintenanceRequestId(id) {
    return axios.post(`/maintenance-request/complete/${id}`)
  }

}
