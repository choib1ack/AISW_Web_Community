// 출처 : https://kdinner.tistory.com/68
export default function TimeExpression (value) {
    const now = new Date();
    const timeValue = new Date(value);

    const betweenTime = Math.floor((now.getTime() - timeValue.getTime()) / 1000 / 60);
    if (betweenTime < 1) return '방금';
    if (betweenTime < 60) {
        return `${betweenTime}분 전`;
    }

    const betweenTimeHour = Math.floor(betweenTime / 60);
    if (betweenTimeHour < 24) {
        return `${betweenTimeHour}시간 전`;
    }

    const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
    if (betweenTimeDay <= 7) {
        return `${betweenTimeDay}일 전`;
    }else{
        return value.substring(5, 7) + "/" + value.substring(8, 10) + "  " + value.substring(11, 16); //YYYY-MM-DD
    }

    return `${Math.floor(betweenTimeDay / 365)}년 전`;
}