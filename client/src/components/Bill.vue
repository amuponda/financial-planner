<template>
  <div class="container">
    <div class="row mt-5">
      <!-- bill summary -->
      <div class="col-md-4">
        <div class="card">
          <div class="card-body">
            <h5>Bill Summary</h5>
            <div>
              <strong>Amount:&nbsp;</strong>
              <strong>{{bill.amount | currency }}&nbsp;</strong>
              <strong><button class="btn btn-primary btn-sm">Edit</button></strong><br>

              <strong>Repeats:&nbsp;</strong>
              <strong>{{ bill.repeats }}</strong><br>

              <strong>Next Due:&nbsp;</strong>
              <strong>{{ bill.nextDue }}</strong><br>
            </div>
            <div>
              <strong>This FY:&nbsp;</strong>
              <strong>$0.00</strong><br>

              <strong>Grand Total:&nbsp;</strong>
              <strong>$0.00</strong>
            </div>
          </div>
        </div>
      </div>

      <!-- graph + transactions -->
      <div class="col-md-8">
        <div>
          <h3 v-text="bill.name" class="text-center"></h3>
        </div>
        <div class="card">
          <div class="card-body table-responsive">
            <h5 class="card-title">Recent Transactions</h5>
            <table class="table table-striped table-sm">
              <thead>
              <tr>
                <th>Date</th>
                <th>Name</th>
                <th>Category</th>
                <th>Amount</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="trans in bill.transactions" :key="trans.id">
                <td>{{ trans.date }}</td>
                <td>{{ bill.name }}</td>
                <td>{{ bill.category }}</td>
                <td>{{ trans.amount | currency }}</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import store from '../vuex/store'
import { mapGetters } from 'vuex'

export default {
  name: 'Bill',
  computed: mapGetters({
    bill: 'getCurrentBill'
  }),
  created () {
  },
  beforeRouteEnter (to, from, next) {
    store.dispatch('getBill', to.params.id).then(() => {
      next()
    })
      .catch(reason => {
        console.error(JSON.stringify(reason.response.data))
      })
  },
  beforeRouteUpdate (to, from, next) {
    this.$store.dispatch('getBill', to.params.id).then(() => {
      next()
    })
      .catch(reason => {
        console.error(JSON.stringify(reason.response.data))
      })
  }
}
</script>

<style scoped>

</style>
