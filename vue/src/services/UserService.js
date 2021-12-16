import axios from 'axios';

export default {

  getUsersByRole(role) {
      return axios.get('/user?role=' + role)
  },

  updateUser(user){
      return axios.put('/user', user);
  }

}
