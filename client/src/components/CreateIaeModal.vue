<template>
  <div :class="['modal', 'fade', {'show': show}]" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">{{ modalHeader }}</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="closeModal">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="submit" novalidate>
            <div class="form-group row">
              <label for="account" class="col-sm-2">Account</label>
              <div class="col-sm-10">
                <select class="form-control" v-model="bill.account" name="account" id="account" required v-validate>
                  <option :value="null">Choose account</option>
                  <option v-for="account in accounts" :key="account.id" :value="account.id">{{account.name}}</option>
                </select>
                <p v-show="errors.has('account')" class="text-danger text-right small mt-1">{{ errors.first('account') }}</p>
              </div>
            </div>
            <div class="form-group row">
              <label for="name" class="col-sm-2">Name</label>
              <div class="col-sm-10">
                <input v-model="bill.name" name="name" id="name" required v-validate class="form-control" placeholder="Name" />
                <p v-show="errors.has('name')" class="text-danger text-right small mt-1">{{ errors.first('name') }}</p>
              </div>
            </div>
            <div class="form-group row">
              <label for="amount" class="col-sm-2">Amount</label>
              <div class="col-sm-10">
                <input v-model="bill.amount" class="form-control" name="amount" id="amount" required v-validate="'decimal:2'" placeholder="Amount" />
                <p v-show="errors.has('amount')" class="text-right text-danger small mt-1">{{ errors.first('amount') }}</p>
              </div>
            </div>
            <div class="form-group row">
              <label for="category" class="col-sm-2">Category</label>
              <div class="col-sm-10">
                <select class="form-control" v-model="bill.category" name="category" id="category" required v-validate>
                  <option :value="null">Choose category</option>
                  <option v-for="cat in categories" :key="cat.name" :value="cat.name">{{ cat.label }}</option>
                </select>
                <p v-show="errors.has('category')" class="text-right text-danger small mt-1">{{ errors.first('category') }}</p>
              </div>
            </div>
            <div class="form-group row">
              <label for="startDate" class="col-sm-2">Starts</label>
              <div class="col-sm-10">
                <datepicker
                  v-model="bill.startDate"
                  name="startDate"
                  id="startDate"
                  required
                  v-validate
                  placeholder="Starts"
                  input-class="form-control"
                  format="dd/MM/yyyy">
                </datepicker>
                <p v-show="errors.has('startDate')" class="text-danger text-right small mt-1">{{ errors.first('startDate') }}</p>
              </div>
            </div>
            <div class="form-group row">
              <label for="repeats" class="col-sm-2">Repeats</label>
              <div class="col-sm-10">
                <select class="form-control" v-model="bill.repeats" name="repeats" id="repeats" required v-validate>
                  <option v-for="period in periodicity" :key="period.name" :value="period.name">{{ period.label }}</option>
                </select>
                <p v-show="errors.has('repeats')" class="text-right text-danger small mt-1">{{ errors.first('repeats') }}</p>
              </div>
            </div>
            <div class="form-group row" v-show="bill.repeats !== 'ONCE_OFF'">
              <label for="endDate" class="col-sm-2">Ends</label>
              <div class="col-sm-10">
                <datepicker
                  v-model="bill.endDate"
                  name="endDate"
                  id="endDate"
                  v-validate
                  input-class="form-control"
                  format="dd/MM/yyyy"
                  placeholder="Can be blank">
                </datepicker>
              </div>
            </div>
            <button type="submit" class="btn btn-primary btn-sm">Add</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import dayjs from 'dayjs'
import Datepicker from 'vuejs-datepicker'
import { mapGetters } from 'vuex'

const initialData = {
  bill: {
    account: null,
    name: null,
    amount: null,
    category: null,
    repeats: null,
    startDate: new Date()
  }
}

export default {
  name: 'CreateBillModal',
  computed: {
    ...mapGetters({
      categories: 'getCategories',
      periodicity: 'getRepeats'
    }),
    modalHeader () {
      return this.type === 'INCOME' ? 'Add Income' : 'Add Bill'
    }
  },
  components: { Datepicker },
  props: {
    show: {
      type: Boolean,
      default: false
    },
    accounts: {
      type: Array,
      required: true
    },
    type: {
      type: String,
      validator (value) {
        return ['INCOME', 'EXPENSE'].indexOf(value) !== -1
      }
    }
  },
  data () {
    return {
      bill: Object.assign({}, initialData.bill)
    }
  },
  watch: {
    show (val) {
      if (val && this.type === 'INCOME' && !this.bill.category) {
        this.bill.category = this.categories.find(cat => cat.name === this.type).name
      }
    }
  },
  methods: {
    closeModal () {
      this.resetForm()
      this.$emit('on-close')
    },
    submit () {
      this.$validator.validateAll().then(result => {
        if (result) {
          this.addBill()
        }
      })
    },
    addBill () {
      let params = new URLSearchParams()
      params.append('account.id', this.bill.account)
      params.append('name', this.bill.name)
      params.append('category', this.bill.category)
      params.append('repeats', this.bill.repeats)
      params.append('startDate', dayjs().format('DD/MM/YYYY'))
      params.append('amount', this.bill.amount)
      params.append('type', this.type)

      this.$store.dispatch('addBill', params).then(result => {
        this.resetForm()
      })
        .catch(reason => {
          // handle backend errors here
          console.error(JSON.stringify(reason))
        })
    },
    resetForm () {
      this.bill = Object.assign({}, initialData.bill)
      this.errors.clear()
      this.$validator.reset()
    }
  }
}
</script>

<style scoped>
  .modal-dialog {
    margin-top: 4rem;
  }
  .modal.show {
    display: block;
    background-color: rgba(0, 0, 0, .5);
  }
</style>
