import React, {useEffect, useState} from "react";
import Loading from "../Loading";
import Container from "react-bootstrap/Container";
import Title from "../Title";
import Button from "react-bootstrap/Button";
import Table from "react-bootstrap/Table";
import axios from "axios";
import axiosApi from "../../axiosApi";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {useSelector} from "react-redux";

function ManageUserAuth() {

    const [userAuthData, setUserAuthData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const [refresh, setRefresh] = useState(0);



    const Refresh = () => {
        setRefresh(refresh + 1);
    }

    useEffect(() => {
        const fetchUserAuthData = async () => {
            try {
                setError(null);
                setLoading(true);

                await axiosApi.get("/auth-admin/users")
                    .then((res)=>{
                        setUserAuthData(res.data.data);
                    });

            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchUserAuthData();
    }, [refresh]);


    if (loading) return <Loading/>;
    if (error) return <p> 에러가 발생했습니다{error.toString()}</p>;
    // if (!userAuthData) return null;

    return (
        <div className='ManageFAQ'>
            <Container>
                <Title text='관리자' type='1'/>
                <Title text='회원 권한 관리' type='2'/>

                <div id="user_auth_table" className="pt-3 pb-5">
                    <Table>
                        <thead>
                        <tr>
                            <th style={{width: "10%"}}>no</th>
                            <th style={{width: "25%"}}>이름</th>
                            <th style={{width: "25%"}}>학번</th>
                            <th style={{width: "40%"}}>권한</th>
                        </tr>
                        </thead>

                        <tbody className={'english_table type2'}>

                        {userAuthData!=null?userAuthData.map((data, index) =>(
                            <UserAuthManageItem
                                index={index}
                                data={data}
                                Refresh={Refresh}
                            />
                        )):null}
                        </tbody>
                    </Table>
                </div>

            </Container>
        </div>
    );

}export default ManageUserAuth;

function UserAuthManageItem({index, data, Refresh}) {
    const [updateModalShow, setUpdateModalShow] = useState(false);

    const handleFaqModalShow = () => setUpdateModalShow(true);

    return(
        <tr key={index}>
            <td>{index+1}</td>
            <td>{data.name}</td>
            <td>{data.email}</td>
            <td style={{verticalAlign:"middle"}}>
                    {data.role}
                <Button size='sm'  onClick={handleFaqModalShow} className='ml-3'>
                    수정
                </Button>
            </td>

            {updateModalShow?
                <UserAuthModal
                    id={data.id}
                    show={updateModalShow}
                    setShow={setUpdateModalShow}
                    name={data.name}
                    email={data.email}
                    role={data.role}
                    Refresh={Refresh}
                />:null}

        </tr>
    );
}

function UserAuthModal(props){

    const [role, setRole] = useState(props.role);

    const admin_role = useSelector(state => state.user.decoded.role);

    const handleRoleChange = (event) => {
        setRole(event.target.value);
    }

    const modalClose = () => {
        props.setShow(false);
    }

    const handleSubmit = () => {
        axiosApi.put('/auth-admin/user',
            {
                data: {
                    id: props.id,
                    name: props.name,
                    email: props.email,
                    role: role
                }
            })
            .then((res) => {
                modalClose();
                alert("회원 권한을 변경했습니다.");
                props.Refresh();
            })
            .catch(error => {
                console.log(error);
                alert("회원 권한 변경에 실패했습니다.");
            })
    }

    return(
        <>
            <div className="FaqModal">
                <Modal show={props.show} onHide={modalClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>회원 권한 수정</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>

                        <select value={role}
                                onChange={handleRoleChange}>
                            <option value="ROLE_GENERAL">ROLE_GENERAL</option>
                            <option value="ROLE_STUDENT">ROLE_STUDENT</option>
                            <option value="ROLE_COUNCIL">ROLE_COUNCIL</option>
                            {admin_role === 'ROLE_DEVELOPER'?<option value="ROLE_ADMIN">ROLE_ADMIN</option>:null}
                        </select>

                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="primary" type="submit" onClick={handleSubmit}>
                            수정
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>
        </>
    );

}
