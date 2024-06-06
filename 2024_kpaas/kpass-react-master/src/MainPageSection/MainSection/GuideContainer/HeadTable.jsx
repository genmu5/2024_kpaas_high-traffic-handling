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
    padding: 10px;
    border: 1px solid #ddd;
    text-align: center;
    cursor: pointer;
    background-color: #f9f9f9;
    &:hover {
        background-color: #f1f1f1;
    }
`;

const HeadTable = (onItemClick) => {
    const handleItemClick = (item) => {
        console.log(`Clicked on ${item}`);
    };

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
                    <TableCell onClick={() => onItemClick('호우')}>호우</TableCell>
                    <TableCell onClick={() => onItemClick('낙뢰')}>낙뢰</TableCell>
                    <TableCell onClick={() => onItemClick('해일')}>해일</TableCell>
                    <TableCell onClick={() => onItemClick('대설')}>대설</TableCell>
                </tr>
                <tr>
                    <TableCell onClick={() => onItemClick('폭염')}>폭염</TableCell>
                    <TableCell onClick={() => onItemClick('황사')}>황사</TableCell>
                    <TableCell onClick={() => onItemClick('대설')}>대설</TableCell>
                    <TableCell onClick={() => onItemClick('한파')}>한파</TableCell>
                </tr>
                </tbody>
            </StyledTable>
        </TableContainer>
    );
};

export default HeadTable;
