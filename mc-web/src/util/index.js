/**
 * 判断非空值
 *
 * @param value 被判断的值
 * @return boolean 返回 true 表示不为 null 也不为 undefined
 */
export function isNotNull(value) {
    return value !== null && value !== undefined;
}

/**
 * 判断空值
 *
 * @param value 被判断的值
 * @return boolean 返回 true 表示为 null 或 undefined
 */
export function isNull(value) {
    return !isNotNull(value);
}

/**
 * 判断是否存在空值
 *
 * @param values 被判断的值，不定长列表
 * @return boolean 返回 true 表示包含 null 或 undefined
 */
export function hasNull(...values) {
    for (let value in values) {
        if (isNull(value)) {
            return true;
        }
    }
    return false;
}

/**
 * 判断空字符串
 *
 * @param value 被判断的值
 * @return boolean 返回 true 表示不为 null 或 undefined 或空字符串
 */
export function isNotEmpty(value) {
    return value !== null && value !== undefined && value !== '';
}

/**
 * 判断非空字符串
 *
 * @param value 被判断的值
 * @return boolean 返回 true 表示为 null 或 undefined 或空字符串
 */
export function isEmpty(value) {
    return !isNotEmpty(value);
}

/**
 * 判断是否存在空字符串
 *
 * @param values 被判断的值，不定长列表
 * @return boolean 返回 true 表示包含 null 或 undefined 或空字符串
 */
export function hasEmpty(...values) {
    for (let value in values) {
        if (isEmpty(value)) {
            return true;
        }
    }
    return false;
}

// 用于将六大元素处理为两位数格式（年除外）
function toDouble(e) {
    return e < 10 ? '0' + e : e;
}

/**
 * 日期字符串处理：1999-01-02T12:12:12 -> 1999年01月02日 12:12
 *
 * @param dateStr 日期字符串
 * @return string 返回格式化后的日期字符串
 */
export function datetimeFormat(dateStr) {
    if (isNull(dateStr)) return '';
    // 将日期字符串转为日期格式
    let date = new Date(dateStr);
    // 获取日期中的元素: 年，月，日，时，分
    let yy = toDouble(date.getFullYear());
    let mm = toDouble(date.getMonth() + 1);
    let dd = toDouble(date.getDate());
    let hh = toDouble(date.getHours());
    let mi = toDouble(date.getMinutes());
    // 返回美化后的日期字符串
    return `${yy}年${mm}月${dd}日 ${hh}:${mi}`;
}

/**
 * 日期字符串处理：1999-01-02T12:12:12 -> 1999年01月02日
 *
 * @param dateStr 日期字符串
 * @return string 返回格式化后的日期字符串
 */
export function dateFormat(dateStr) {
    if (isNull(dateStr)) return '';
    // 将日期字符串转为日期格式
    let date = new Date(dateStr);
    // 获取日期中的元素: 年，月，日，时，分
    let yy = toDouble(date.getFullYear());
    let mm = toDouble(date.getMonth() + 1);
    let dd = toDouble(date.getDate());
    // 返回美化后的日期字符串
    return `${yy}年${mm}月${dd}日`;
}

/**
 * 性别代码处理：0->'女'，1->'男'，2->'保密'
 *
 * @param genderCode 性别代码
 * @return string 对应的性别字符串，0女孩，1男孩，2保密
 * */
export function genderFormat(genderCode) {
    if (genderCode === '0' || genderCode === 0) return '女孩';
    if (genderCode === '1' || genderCode === 1) return '男孩';
    if (genderCode === '2' || genderCode === 2) return '保密';
    return '性别代码异常';
}

/**
 * 学生状态代码处理：0->'在培'，1->'结课'，2->'已就业'，3->'休学'，4->'其它'
 *
 * @param stateCode 学生状态代码
 * @return string 对应的学生状态字符串，0在培，1结课，2已就业，3休学，4其它
 * */
export function studentStateFormat(stateCode) {
    if (stateCode === '0' || stateCode === 0) return '在培';
    if (stateCode === '1' || stateCode === 1) return '结课';
    if (stateCode === '2' || stateCode === 2) return '已就业';
    if (stateCode === '3' || stateCode === 3) return '休学';
    if (stateCode === '4' || stateCode === 4) return '其它';
    return '学生状态代码异常';
}


