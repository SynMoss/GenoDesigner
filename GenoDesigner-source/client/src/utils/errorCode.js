const zh = {
  '401': 'Authentication failed, unable to access system resources',
  '403': 'The current operation lacks permission',
  '404': 'The requested resource does not exist',
  'default': 'System unknown error, please provide feedback to the administrator'
}
const en = {
  '401': 'Authentication failed, unable to access system resources',
  '403': 'The current operation lacks permission',
  '404': 'The requested resource does not exist',
  'default': 'System unknown error, please provide feedback to the administrator'
}
export default process.env.VUE_APP_LANGUAGE==='zh'?zh:en;
