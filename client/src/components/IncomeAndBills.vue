<template>
  <div :class="['container', {'modal-open': showModal}]">
    <div class="row mt-5">
      <div class="col-md-4">
        <!-- bills summary -->
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Bill Summary</h5>
            <div>
              <strong>Monthly Total:&nbsp;</strong>
              <strong>$0.00</strong><br>

              <strong>Month To Date:&nbsp;</strong>
              <strong>$0.00</strong><br>

              <strong>Month Remaining:&nbsp;</strong>
              <strong>$0.00</strong>
            </div>
            <div class="mt-3">
              <button type="button" class="btn btn-primary btn-sm" @click="addIncomeOrExpense('EXPENSE')">Add Bill</button>
            </div>
          </div>
          <div class="card-body">
            <h5 class="card-title">Income Summary</h5>
            <div>
              <strong>Monthly Total:&nbsp;</strong>
              <strong>$0.00</strong><br>

              <strong>Month To Date:&nbsp;</strong>
              <strong>$0.00</strong><br>

              <strong>Month Remaining:&nbsp;</strong>
              <strong>$0.00</strong>
            </div>
            <div class="mt-3">
              <button type="button" class="btn btn-primary btn-sm" @click="addIncomeOrExpense('INCOME')">Add Income</button>
            </div>
          </div>
        </div>

      </div>

      <!--IaE list -->
      <div class="col-md-8">
        <div class="card">
          <div class="card-body table-responsive">
            <h5 class="card-title">Income and Expenses  <span v-if="!isBills" class="badge badge-info">No Expenses</span></h5>
            <table v-if="isBills" class="table table-striped table-sm">
              <thead>
              <tr>
                <th>Since</th>
                <th>Name</th>
                <th>Category</th>
                <th>Amount</TH>
              </tr>
              </thead>
              <tbody>
              <tr v-for="bill in iae" :key="bill.id">
                <td>{{ bill.startDate | date }}</td>
                <td><router-link :to="{ name: 'bill', params: { id: bill.id }}">{{ bill.name }}</router-link></td>
                <td>{{ bill.category | enumPrettify(categories) }}</td>
                <td>{{ bill.amount | currency }}</td>
              </tr>
              </tbody>
            </table>
            <!--
            <span v-else class="badge badge-info">You do not currently have any bill or income setup</span>
            -->
          </div>
        </div>
      </div>
    </div>

    <create-iae-modal :accounts="accounts" :show="showModal" :type="type" @on-close="hideModal"></create-iae-modal>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import CreateIaeModal from './CreateIaeModal'
import modalMixin from '../mixins/ModalMixin'
import dayjs from 'dayjs'

export default {
  name: 'Bills',
  data () {
    return {
      type: null
    }
  },
  mixins: [modalMixin],
  computed: {
    ...mapGetters({
      accounts: 'getAccounts',
      iae: 'getIae',
      isInitIae: 'isInitIae',
      categories: 'getCategories'
    }),
    isBills () {
      return this.iae.length > 0
    }
  },
  components: { CreateIaeModal },
  filters: {
    date (value) {
      if (!value) return '-'
      return dayjs(value).format('DD/MM/YYYY')
    },
    enumPrettify (value, enums) {
      if (!enums) return value
      let pretty = enums.find(e => e.name === value)
      return pretty ? pretty.label : value
    }
  },
  methods: {
    addIncomeOrExpense (type) {
      this.type = type
      this.toggleModal()
    },
    hideModal () {
      this.type = null
      this.toggleModal()
    }
  },
  created () {
    if (!this.isInitIae) {
      this.$store.dispatch('fetchIncomeAndExpenditure')
    }
  }
}
</script>

<style scoped>
</style>
