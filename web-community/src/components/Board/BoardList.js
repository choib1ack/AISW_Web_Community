import Container from "react-bootstrap/Container";
import Title from "../Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import searchImage from "../../icon/search.svg";
import Table from "react-bootstrap/Table";
import photoImage from "../../icon/photo.svg";
import fileImage from "../../icon/file.svg";
import Button from "react-bootstrap/Button";
import classNames from "classnames";
import Pagination from "../PaginationCustom";
import React, {useState} from "react";
import {Link} from "react-router-dom";
import {useHistory} from "react-router-dom";

function BoardList({match}) {
    let history = useHistory();
    const [category, setCategory] = useState(0);

    let lists;

    function handleClick() {
        history.push("/newBoard");
    }

    return (
        <div className="Board">
            <Container>
                <Title text='게시판' type='1'/>
                <Row style={{marginBottom: '1rem', marginTop: '2rem'}}>
                    <Col lg={6} md={8} sm={12}>
                        <SelectButton
                            id='0' title='전체' active={category}
                            onClick={()=>setCategory(0)}
                        />
                        <SelectButton
                            id='1' title='자유게시판' active={category}
                            onClick={()=>setCategory(1)}
                            />
                        <SelectButton
                            id='2' title='과목별게시판' active={category}
                            onClick={()=>setCategory(2)}
                        />
                    </Col>
                    <Col lg={6} md={4} sm={12}>
                        <img src={searchImage} style={{float: "right", marginLeft: "10px", height: "25px"}}/>
                        <input type="text" className={"search-box"} placeholder={'검색'}/>
                    </Col>
                </Row>
                <Table>
                    <thead>
                    <tr>
                        <th style={{width: "10%"}}>no</th>
                        <th style={{width: "55%"}}>제목</th>
                        <th style={{width: "10%"}}>작성자</th>
                        <th style={{width: "10%"}}>등록일</th>
                        <th style={{width: "10%"}}>조회</th>
                    </tr>
                    </thead>
                    <tbody>
                    {lists = makeList(match, category)}
                    </tbody>
                </Table>
                <Row>
                    <Col lg={12} md={12} sm={12}>
                        <Button className={classNames("select-btn", "on")}
                                style={{float: 'right'}} onClick={() => handleClick()}>
                            글쓰기
                        </Button>
                    </Col>
                </Row>
                <Pagination active={1}/>
            </Container>
        </div>
    );
}export default BoardList;

// 게시판 카테고리에 맞는 리스트를 만들어주는 함수
// 0: 전체, 1: 자유게시판, 2: 과목별게시판
function makeList(match, category) {
    // test data
    let data = [
        {
            category: 1,
            subject: '[멀티캠퍼스] 코딩테스트 대회 안내 파일1',
            attached: 0,
            writer: '양희림',
            date: '2021-01-16',
            count: 1
        },
        {
            category: 2,
            subject: 'AI·소프트웨어학부 플러스친구 안내1',
            attached: 1,
            writer: '양희림',
            date: '2021-01-16',
            count: 1
        },
        {
            category: 2,
            subject: '산학과제 배정 공고',
            attached: 0,
            writer: '양희림',
            date: '2021-01-16',
            count: 1
        },
        {
            category: 2,
            subject: 'ICT학점연계프로젝트인턴십 사업 안내',
            attached: 0,
            writer: '양희림',
            date: '2021-01-16',
            count: 1
        },
        {
            category: 1,
            subject: '21학번 새내기 단톡방 개설 안내',
            attached: 1,
            writer: '양희림',
            date: '2021-01-16',
            count: 1
        },
        {
            category: 2,
            subject: 'AI·소프트웨어학부 플러스친구 안내2',
            attached: 2,
            writer: '양희림',
            date: '2021-01-16',
            count: 1
        }
    ];
    const list = (i, no, max_no) => {
        return(
            <tr key={max_no - no}>
                <td>{max_no - no}</td>
                <td>
                    <Link to={`${match.url}/10`} style={{color: 'black'}}>
                        {data[i].subject}
                        <img src={photoImage} style={{marginLeft: '5px'}}/>
                        <img src={fileImage} style={{marginLeft: '5px'}}/>
                    </Link>
                </td>
                <td>{data[i].writer}</td>
                <td>{data[i].date}</td>
                <td>{data[i].count}</td>
            </tr>
        );
    }

    let lists = [];
    if (category === 0) {
        for (let i = 0; i < Object.keys(data).length; i++) {
            lists.push(
                list(i, i, Object.keys(data).length)
            );
        }
    } else {
        let no = 0;
        let count = 0;
        for (let i = 0; i < Object.keys(data).length; i++) {
            if (data[i].category === category) {
                count++;
            }
        }
        // i는 데이터 index, no는 몇번째인지, count는 한 페이지에서 표시되는 최대 no의 숫자
        for (let i = 0; i < Object.keys(data).length; i++) {
            if (data[i].category === category) {
                lists.push(
                    list(i, no, count)
                );
                no++;
            }
        }
    }

    return lists;
}

function SelectButton(props) {
    let active;
    if(props.active == props.id){
        active = true;
    }else{
        active = false;
    }

    let btnStyle = {
        float: 'left',
        margin: '0.5rem',
        border: '0',
        outline: 'none',
        boxShadow: 'none',
        backgroundColor: active ? '#6CBACB' : '#F4F4F4',
        color: active ? '#ffffff' : '#B8B8B8'
    }
    return (
        <Button style={btnStyle} onClick={props.onClick}>{props.title}</Button>
    );
}
