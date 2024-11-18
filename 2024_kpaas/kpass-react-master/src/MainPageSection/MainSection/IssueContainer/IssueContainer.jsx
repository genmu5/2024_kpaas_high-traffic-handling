import React, { useRef, useEffect, useState } from 'react';
import styled from 'styled-components';
import { useRecoilValue } from 'recoil';
import useCurrentLocation from '../../../hooks/useCurrentLocation';
import { currentLocationAtom } from '../../../state/currentLocationAtom';

// 재난 유형에 따른 색상 정의
const disasterColors = {
    "지진": 'red',
    "산사태": 'orange',
    "조수": 'blue',
    "폭염": 'yellow',
    "풍수해": 'green',
    "감염병": 'purple',
    "다중밀집건축물붕괴대형사고": 'black',
    "산불": 'brown',
    "초미세먼지": 'gray',
    "해양선박사고": 'darkblue',
    "한파": 'lightblue',
    "화재": 'darkred',
    "호우": 'lightgreen'
};

// 새로운 마커 스타일 정의
const MarkerStyle = `
    width: 24px; 
    height: 24px; 
    background-color: {color}; 
    border-radius: 50%; 
    display: flex; 
    justify-content: center; 
    align-items: center; 
    color: #fff; 
    font-size: 14px; 
    font-weight: bold; 
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.3); 
    transition: transform 0.2s ease;
`;

// 중앙 부분만 스타일 변경
const Container = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 650px;
    background-color: #ffffff;
    padding: 20px;
    border-radius: 12px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
    gap: 15px;
    margin: 20px 0;
`;

const Title = styled.div`
    font-size: 28px;
    font-weight: 600;
    color: #333;
    margin-bottom: 10px;
    text-align: left;
    width: 100%;
    border-bottom: 2px solid #ddd;
    padding-bottom: 8px;
`;

const MapWrapper = styled.div`
    width: 100%;
    height: 500px;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
`;

const IssueContainer = () => {
    useCurrentLocation();
    const currentLocation = useRecoilValue(currentLocationAtom);
    const mapRef = useRef(null);
    const [disasterData, setDisasterData] = useState([]);
    const markersRef = useRef([]);
    const infoWindowsRef = useRef([]);

    useEffect(() => {
        const fetchDisasterData = async () => {
            try {
                const response = await fetch(`http://211.188.55.193:8080/api/disasters/location`);
                const data = await response.json();
                console.log("Fetched disaster data:", data);
                setDisasterData(data);
            } catch (error) {
                console.error("Error fetching disaster data:", error);
            }
        };

        fetchDisasterData();
    }, []);

    useEffect(() => {
        const naverMapClientId = process.env.REACT_APP_NAVER_MAP_CLIENT_ID;
        const script = document.createElement('script');
        script.src = `https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${naverMapClientId}&submodules=geocoder`;
        script.async = true;
        document.head.appendChild(script);

        script.onload = () => {
            if (window.naver) {
                const defaultCenter = new window.naver.maps.LatLng(37.554722, 126.970833);
                mapRef.current = new window.naver.maps.Map('map', {
                    center: defaultCenter,
                    zoom: 7,
                    minZoom: 6,
                    zoomControl: true,
                    mapTypeControl: true,
                    zoomControlOptions: {
                        position: window.naver.maps.Position.TOP_RIGHT,
                    },
                    mapDataControl: false,
                });
            }
        };

        return () => {
            document.head.removeChild(script);
        };
    }, []);

    useEffect(() => {
        if (mapRef.current && disasterData.length > 0) {
            markersRef.current.forEach(marker => marker.setMap(null));
            infoWindowsRef.current.forEach(infoWindow => infoWindow.close());
            markersRef.current = [];
            infoWindowsRef.current = [];

            disasterData.forEach((disaster) => {
                const { latitude, longitude, disasterType, regionName } = disaster;
                const color = disasterColors[disasterType] || 'black';

                if (latitude && longitude) {
                    const position = new window.naver.maps.LatLng(latitude, longitude);

                    const marker = new window.naver.maps.Marker({
                        position,
                        map: mapRef.current,
                        title: `${disasterType} - ${regionName}`,
                        icon: {
                            content: `<div style="${MarkerStyle.replace('{color}', color)}">
                                          ${disasterType.charAt(0)}
                                      </div>`,
                            anchor: new window.naver.maps.Point(12, 12),
                        },
                    });

                    const infoWindow = new window.naver.maps.InfoWindow({
                        content: `<div style="padding:10px; background-color: ${color}; color: #ffffff; border-radius: 5px;">
                                      ${disasterType} - ${regionName}
                                  </div>`,
                        borderWidth: 0,
                        backgroundColor: "transparent",
                    });

                    infoWindowsRef.current.push(infoWindow);

                    let infoWindowOpen = false;
                    window.naver.maps.Event.addListener(marker, "click", () => {
                        if (infoWindowOpen) {
                            infoWindow.close();
                        } else {
                            infoWindow.open(mapRef.current, marker);
                        }
                        infoWindowOpen = !infoWindowOpen;
                    });

                    markersRef.current.push(marker);
                } else {
                    console.warn(`Missing latitude or longitude for disaster: ${disasterType} at ${regionName}`);
                }
            });

            console.log("Markers created:", markersRef.current);
        }
    }, [disasterData]);

    useEffect(() => {
        if (mapRef.current && currentLocation.lat && currentLocation.lng) {
            const targetLocation = new window.naver.maps.LatLng(currentLocation.lat, currentLocation.lng);
            mapRef.current.panTo(targetLocation, { duration: 1000 });
            mapRef.current.setZoom(10, { animate: true, duration: 1000 });
        }
    }, [currentLocation]);

    return (
        <Container>
            <Title>지역별 이슈</Title>
            <MapWrapper id="map"></MapWrapper>
        </Container>
    );
};

export default IssueContainer;

