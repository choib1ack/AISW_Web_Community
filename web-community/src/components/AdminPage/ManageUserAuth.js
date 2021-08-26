import React, {useState} from "react";
import Loading from "../Loading";
import Container from "react-bootstrap/Container";
import Title from "../Title";
function ManageUserAuth() {

    const [userAuthData, setUserAuthData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    if (loading) return <Loading/>;
    if (error) return <p> 에러가 발생했습니다{error.toString()}</p>;
    // if (!userAuthData) return null;

    return (
        <div className='ManageFAQ'>
            <Container>
                <Title text='관리자' type='1'/>
                <Title text='회원 권한 관리' type='2'/>



            </Container>
        </div>
    );

}export default ManageUserAuth;