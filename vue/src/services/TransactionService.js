import axios from 'axios';

export default {
    getAll(){
        return axios.get('/transaction');
    },

    getMyRent(id){
        return axios.get(`/transaction/${id}`)
    },

    payRent(id, amountPaid){
        console.log(amountPaid)
        return axios.put(`/transaction/${id}`,{paymentAmount: amountPaid})
    }
}