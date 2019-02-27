<template>
  <div class="container">
    <div class="row mt-5">
      <!-- bill summary -->
      <div class="col-md-4">
        <div class="card">
          <div class="card-body">
            <h5>Bill Summary</h5>
            <div>
              <form v-if="isEdit" @submit="updateAmount">
                <input v-model="newAmount" />
                <button class="btn btn-primary btn-sm">Save</button>
                <button class="btn btn-primary btn-sm" type="button" @click="toggleEdit(false)">Cancel</button>
              </form>

              <span v-else>
                <strong>Amount:&nbsp;</strong>
                <strong>{{ bill.amount | currency }}&nbsp;</strong>
                <button class="btn btn-primary btn-sm" @click="toggleEdit(true)">Edit</button><br>
              </span>

              <strong>Repeats:&nbsp;</strong>
              <strong>{{ bill.repeats | enumPrettify(paymentPeriods) }}</strong><br>

              <strong>Next Due:&nbsp;</strong>
              <strong>{{ bill.nextDue | date }}</strong><br>
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
                <td>{{ trans.date | date }}</td>
                <td>{{ bill.name }}</td>
                <td>{{ bill.category | enumPrettify(categories) }}</td>
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
  data () {
    return {
      isEdit: false,
      newAmount: null
    }
  },
  computed: mapGetters({
    bill: 'getCurrentBill',
    categories: 'getCategories',
    paymentPeriods: 'getRepeats'
  }),
  methods: {
    toggleEdit (isOn) {
      this.isEdit = isOn
      this.newAmount = isOn ? this.bill.amount : null
    },
    updateAmount () {
      let toUpdate = Object.assign({}, this.bill)
      toUpdate.amount = this.newAmount
      this.$store.dispatch('updateBill', toUpdate).then(() => {
        this.toggleEdit(false)
      })
    }
  },
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
