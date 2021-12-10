<template>
    <v-simple-table>
        <thead>
            <tr>
                <th>property</th>
                <th>date due</th>
                <th>status</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td></td>
            </tr>
        </tbody>
    </v-simple-table>
</template>

<script>
import transactionService from '../services/TransactionService';
export default {
    name: "transactions",
    data(){
        return {
            transactions: null
        };
    },
    created() {
    transactionService.getAll().then((response) => {
      if (response.status === 200) {
        this.transactions = response.data;
      }
    });
  }
    
}
</script>