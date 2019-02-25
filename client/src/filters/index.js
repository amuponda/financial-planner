import dayjs from 'dayjs'

let filters = {
  date (value) {
    if (!value) return '-'
    return dayjs(value).format('DD/MM/YYYY')
  },
  enumPrettify (value, enums) {
    if (!enums) return value
    let pretty = enums.find(e => e.name === value)
    return pretty ? pretty.label : value
  }
}

export default {
  install (Vue) {
    Object.keys(filters).forEach(filter => {
      Vue.filter(filter, filters[filter])
    })
  }
}
