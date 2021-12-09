import axios from 'axios';

export default {

  getUsersByRole(role) {
      return axios.get('/user?role=' + role)
  }

}
