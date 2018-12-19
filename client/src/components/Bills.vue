<template>
  <div class="container">
    <div class="row mt-5">
      <div class="col-md-4">
        <!-- bills summary -->
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Summary</h5>
            <div>
              <strong>Monthly Total:&nbsp;</strong>
              <strong>$0.00</strong><br>

              <strong>Month To Date:&nbsp;</strong>
              <strong>$0.00</strong><br>

              <strong>Month Remaining:&nbsp;</strong>
              <strong>$0.00</strong>
            </div>
            <div class="mt-3">
              <button type="button" class="btn btn-primary btn-sm" @click="toggleModal">Add Bill</button>
            </div>
          </div>
        </div>
      </div>

      <!--Bills list -->
      <div class="col-md-8">
        <div class="card">
          <div class="card-body table-responsive">
            <h5 class="card-title">Bills</h5>
            <table v-if="bills" class="table table-striped table-sm">
              <thead>
              <tr>
                <th>Since</th>
                <th>Name</th>
                <th>Amount</th>
                <th>Category</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="bill in bills" :key="bill.id">
                <td>{{ bill.startDate | date }}</td>
                <td v-text="bill.name"></td>
                <td>{{ bill.amount | currency }}</td>
                <td>{{ bill.category | enumPrettify(categories) }}</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>

    <create-bill-modal :accounts="accounts" :show="showModal" @on-close="toggleModal"></create-bill-modal>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import CreateBillModal from './CreateBillModal'
import modalMixin from '../mixins/ModalMixin'
import dayjs from 'dayjs'

export default {
  name: 'Bills',
  mixins: [modalMixin],
  computed: mapGetters({
    accounts: 'getAccounts',
    bills: 'getBills',
    isInitBills: 'isInitBills',
    categories: 'getCategories'
  }),
  components: { CreateBillModal },
  filters: {
    date (value) {
      if (!value) return '-'
      return dayjs(value).format('DD/MM/YYYY')
    },
    enumPrettify (value, enums) {
      let pretty = enums.find(e => e.name === value)
      return pretty ? pretty.label : value
    }
  },
  created () {
    if (!this.isInitBills) {
      this.$store.dispatch('fetchBills')
    }
  }
}
</script>

<style scoped>
</style>
