import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import './Menu.css';

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
    },
    paper: {
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.secondary,
    },
}));

export default function Menu() {
    const classes = useStyles();

    return (
        <div className={classes.root}>
            <Grid container spacing={3}>
                <Grid item xs>
                    <Paper className={classes.paper} style={{background: 'transparent', boxShadow: 'none'}}>
                        <button className="Menu-logo">
                            가천대학교 AI&소프트웨어학부
                        </button>
                    </Paper>
                </Grid>
                <Grid item xs={6}>
                    <Paper className={classes.paper} style={{background: 'transparent', boxShadow: 'none'}}>
                        <button className="Menu-button">
                            공지사항
                        </button>
                        <button className="Menu-button">
                            게시판
                        </button>
                        <button className="Menu-button">
                            학과정보
                        </button>
                        <button className="Menu-button">
                            채용정보
                        </button>
                        <button className="Menu-button">
                            공모전/대외활동
                        </button>
                    </Paper>
                </Grid>
                <Grid item xs>
                    <Paper className={classes.paper} style={{background: 'transparent', boxShadow: 'none'}}>
                        <button className="Menu-button">
                            로그인
                        </button>
                        <button className="Menu-button blue-button">
                            회원가입
                        </button>
                    </Paper>
                </Grid>
            </Grid>
        </div>
    );
}
