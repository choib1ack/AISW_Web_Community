export const GOOGLE_CLIENT_ID = '1051028847648-3edseaslg7hqbrgo5q2thhdag9k6q10e.apps.googleusercontent.com';
export const GOOGLE_REDIRECT_URI = 'http://localhost:8080/auth/google/callback';

export const ROLE = ['ROLE_GENERAL', 'ROLE_STUDENT', 'ROLE_COUNCIL', 'ROLE_ADMIN', 'ROLE_DEVELOPER'];
export const NOTICE_WRITE_ROLE = ['ROLE_COUNCIL', 'ROLE_ADMIN', 'ROLE_DEVELOPER'];
export const BOARD_WRITE_ROLE = ['ROLE_STUDENT', 'ROLE_COUNCIL', 'ROLE_ADMIN', 'ROLE_DEVELOPER'];

// 공지사항
export const AUTH_NOTICE_GET = {
    'university': 'auth',
    'council': 'auth-student',
    'department': 'auth-student'
};
export const AUTH_NOTICE_POST = {
    'university': 'auth-admin',
    'council': 'auth-council',
    'department': 'auth-admin'
};
export const AUTH_NOTICE_PUT = {
    'university': 'auth-admin',
    'council': 'auth-council',
    'department': 'auth-admin'
};
export const AUTH_NOTICE_DELETE = {
    'university': 'auth-admin',
    'council': 'auth-council',
    'department': 'auth-admin'
};

// 게시판
export const AUTH_BOARD_GET = {
    'free': 'auth',
    'qna': 'auth-student',
    'job': 'auth'
};
export const AUTH_BOARD_POST = {
    'free': 'auth',
    'qna': 'auth-student',
    'job': 'auth'
};export const AUTH_BOARD_PUT = {
    'free': 'auth',
    'qna': 'auth-student',
    'job': 'auth'
};export const AUTH_BOARD_DELETE = {
    'free': 'auth',
    'qna': 'auth-student',
    'job': 'auth'
};
