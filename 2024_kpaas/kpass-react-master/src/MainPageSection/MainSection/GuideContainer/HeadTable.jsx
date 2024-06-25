import React from 'react';
import styled from 'styled-components';

const TableContainer = styled.div`
    width: 100%;
    margin-top: 20px;
`;

const StyledTable = styled.table`
    width: 100%;
    border-collapse: collapse;
`;

const TableCell = styled.td`
    width: 25%;
    padding: 10px;
    border: 1px solid #ddd;
    text-align: center;
    cursor: pointer;
    background-color: #f9f9f9;
    &:hover {
        background-color: #f1f1f1;
    }
`;

const HeadTable = ({ onItemClick }) => {
    return (
        <TableContainer>
            <StyledTable>
                <tbody>
                <tr>
                    <TableCell onClick={() => onItemClick('침수')}>침수</TableCell>
                    <TableCell onClick={() => onItemClick('태풍')}>태풍</TableCell>
                    <TableCell onClick={() => onItemClick('지진')}>지진</TableCell>
                    <TableCell onClick={() => onItemClick('홍수')}>홍수</TableCell>
                </tr>
                <tr>
                    <TableCell onClick={() => onItemClick('대설')}>대설</TableCell>
                    <TableCell onClick={() => onItemClick('낙뢰')}>낙뢰</TableCell>
                    <TableCell onClick={() => onItemClick('해일')}>해일</TableCell>
                    <TableCell onClick={() => onItemClick('한파')}>한파</TableCell>
                </tr>
                <tr>
                    <TableCell onClick={() => onItemClick('폭염')}>폭염</TableCell>
                    <TableCell onClick={() => onItemClick('가뭄')}>가뭄</TableCell>
                    <TableCell onClick={() => onItemClick('우주물체 추락_충돌')}>우주물체 추락</TableCell>
                    <TableCell onClick={() => onItemClick('우주전파')}>우주전파</TableCell>
                </tr>
                </tbody>
            </StyledTable>
        </TableContainer>
    );
};

export default HeadTable;
