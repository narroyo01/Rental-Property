import axios from 'axios';

export default {

  submit(inquiry) {
    return axios.post('/inqury', inquiry)
  },

}
