// src/plugins/dayjs.ts
import dayjs from 'dayjs'
import 'dayjs/locale/ru'
import toObject from 'dayjs/plugin/toObject'
import customParseFormat from 'dayjs/plugin/customParseFormat'
import isToday from 'dayjs/plugin/isToday'
import isBetween from 'dayjs/plugin/isBetween'
import weekday from 'dayjs/plugin/weekday'

dayjs.locale('ru')
dayjs.extend(toObject)
dayjs.extend(customParseFormat)
dayjs.extend(isToday)
dayjs.extend(isBetween)
dayjs.extend(weekday)

export default dayjs
