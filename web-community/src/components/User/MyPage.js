import React, {useEffect, useState} from "react";
import Modal from "react-bootstrap/Modal";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import './MyPage.css';
import {useHistory} from "react-router-dom";
import Loading from "../Loading";
import axiosApi from "../../axiosApi";
import newIcon from "../../icon/new_icon.png"
import {useDispatch, useSelector} from "react-redux";
import {setUnreadAlert} from "../../features/menuSlice";
import CheckModal from "../Modal/CheckModal";
import {resetDecoded} from "../../features/userSlice";
import TimeExpression from "../TimeExpression";



export default function MyPage(props) {
    const [showLogout, setShowLogout] = useState(false);
    const [showWithdrawal, setShowWithdrawal] = useState(false);
    const history = useHistory();

    const [currentPage, setCurrentPage] = useState(0);
    const [loading, setLoading] = useState(false);

    const user = useSelector(state => state.user);
    const {name, department} = useSelector(state => state.user.decoded);
    const dispatch = useDispatch();

    const handleShowLogout = () => setShowLogout(true);
    const handleCloseLogout = () => setShowLogout(false);
    const handleShowWithdrawal = () => setShowWithdrawal(true);
    const handleCloseWithdrawal = () => setShowWithdrawal(false);

    const handleLogout = () => {
        setShowLogout(false);

        window.localStorage.clear();
        history.push('/');  // 홈으로 가기
    }

    const handleWithdrawal = async () => {
        setShowWithdrawal(false);

        await axiosApi.delete('/auth/user').then(r => {
            window.localStorage.clear();
            dispatch(resetDecoded());
            history.push('/');
        }).catch(e => {
            alert("회원탈퇴를 하지 못했습니다.");
        });
    }

    const handleScroll = (e) => {

        const scrollHeight = e.target.scrollHeight;
        const scrollTop = e.target.scrollTop;
        const clientHeight = e.target.clientHeight;

        if (scrollTop + clientHeight >= scrollHeight - 10 && loading === false && currentPage >= 0) {
            // 페이지 끝에 도달하면 추가 데이터를 받아온다
            setCurrentPage(currentPage => currentPage + 1);
        }
    }

    const handleMyPageClose = () => {
        props.setMyPageShow(false);
        setCurrentPage(0);
    }

    return (
        <div>
            <CheckModal show={showLogout}
                        title="로그아웃" body="정말로 로그아웃 하시겠습니까?"
                        handleYes={handleLogout}
                        handleNo={handleCloseLogout}
            />
            <CheckModal show={showWithdrawal}
                        title="회원탈퇴" body="정말로 회원탈퇴 하시겠습니까?"
                        handleYes={handleWithdrawal}
                        handleNo={handleCloseWithdrawal}
            />

            <Modal show={props.myPageShow} aria-labelledby="contained-modal-title-vcenter" bsPrefix="MyPage"
                   onHide={handleMyPageClose}>
                <Modal.Header closeButton style={{border: 'none', paddingBottom: 10}}>
                    <Modal.Title
                        id="contained-modal-title-vcenter"
                        style={{fontSize: '16px'}}
                    >
                        내 정보
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body className="show-grid" style={{padding: "0px"}}>
                    <Container style={{padding: 'none'}}>
                        <Row className="ml-auto mt-3 mb-4">
                            <img className="align-self-center" width={40} src={user.imageUrl}
                                 style={{borderRadius: 50, marginLeft: 10}}
                                 alt="..."/>
                            <Col style={{display: 'flex', justifyContent: 'space-between'}}>
                                <div style={{marginLeft: "10px"}}>
                                    <p style={{fontSize: '14px', marginBottom: "0px"}}>{name}</p>
                                    <p style={{
                                        fontSize: '12px',
                                        color: '#8C8C8C'
                                    }}> {department}</p>
                                </div>
                                <div>
                                    <p onClick={handleShowWithdrawal} className="withdrawal-btn">회원탈퇴</p>
                                    <p onClick={handleShowLogout} className="logout-btn">로그아웃</p>
                                </div>
                            </Col>
                        </Row>

                        <p style={{color: '#0472FD', margin: 10}}>알림</p>

                        <div style={{
                            height: '200px',
                            backgroundColor: '#e7f1ff',
                            border: '1px solid #E3E3E3',
                            overflow: 'auto',
                            borderRadius: '10px',
                            marginBottom: '10px'
                        }}
                             onScroll={handleScroll}>
                            <MakeAlertList
                                history={history}
                                currentPage={currentPage}
                                setLoading={setLoading}
                                setCurrentPage={setCurrentPage}
                            />
                        </div>
                    </Container>
                </Modal.Body>
            </Modal>
        </div>
    );
}

function MakeAlertList(props) {

    // const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [alertList, setAlertList] = useState({list: [], page_info: null});

    let style = {
        borderRadius: '10px',
        backgroundColor: '#FFFFFF',
        border: '1px solid #E3E3E3',
        margin: '10px 20px 10px 20px',
        padding: '15px',
        height: '70px',
        cursor: 'pointer'
    }
    let style_viewed = {
        borderRadius: '10px',
        backgroundColor: '#FFFFFF',
        border: '1px solid #E3E3E3',
        margin: '10px 20px 10px 20px',
        padding: '15px',
        height: '70px',
        cursor: 'pointer',
        opacity: '0.6'
    }

    const returnBoardName = (category) => {
        switch (category) {
            case "FREE":
                return "자유게시판";
            case "QNA":
                return "과목별게시판";
            case "JOB":
                return "취업게시판";
        }
    }

    const returnAlertType = (alert_category) => {
        switch (alert_category) {
            case "COMMENT":
                return "새로운 댓글이 등록되었습니다";
            case "NESTED_COMMENT":
                return "새로운 대댓글이 등록되었습니다";
            case "LIKE_POST":
                return "누군가 게시물에 좋아요를 눌렀습니다";
            case "LIKE_COMMENT":
                return "누군가 댓글에 좋아요를 눌렀습니다";
        }
    }


    const dispatch = useDispatch();
    const ToLink = async (data) => {

        await axiosApi.put("/auth/alert/" + data.id)
            .then(res=>{
                dispatch(setUnreadAlert(res.data));
            });

        props.history.push(`/board/${data.second_category.toLowerCase()}/${data.post_id}`);
    }

    const fetchMyPageData = async () => {

        try {

            if (alertList.page_info != null && props.currentPage + 1 > alertList.page_info.total_pages) {
                return;
            }

            props.setLoading(true);
            setError(null);

            await axiosApi.get("/auth/alert?page=" + props.currentPage)
                .then(res => {

                        let items = [];
                        res.data.data.map((data, index) => (
                            items.push(
                                <div key={index} style={!data.checked ? style : style_viewed}
                                     onClick={() => ToLink(data)}
                                >
                                    <p style={{
                                        float: 'right',
                                        fontSize: '11px',
                                        color: '#8C8C8C'
                                    }}>{TimeExpression(data.created_at)}</p>
                                    <p style={{
                                        fontSize: '12px',
                                        marginBottom: '5px'
                                    }}>{returnBoardName(data.second_category)}
                                        {data.checked ? null :
                                            <img src={newIcon}
                                                 style={{width: "12px", height: "12px", marginLeft: "5px"}}/>}</p>
                                    <p style={{fontSize: '11px', margin: 'none', color: '#8C8C8C'}}>
                                        {returnAlertType(data.alert_category) + "  : " + data.content}
                                    </p>
                                </div>
                            ))
                        );
                        setAlertList({
                            list: [alertList.list.concat(items)],
                            page_info: res.data.pagination
                        });
                    props.setLoading(false);
                    }
                );

        } catch (e) {
            setError(e);
        }

    };


    useEffect(() => {
        fetchMyPageData();
    }, [props.currentPage]);


    if (error) return <div>에러가 발생했습니다{error.toString()}</div>;
    if (props.loading) return <Loading/>;
    if (alertList.list.length === 0) return <div>데이터가 없습니다.</div>;

    return (
        <>
            {alertList.list}

        </>
    );
}


