import React, { useEffect, useState } from 'react';
import axios from 'axios';

const App = () => {
    const [data, setData] = useState('');

    useEffect(() => {
        axios.get('http://localhost:8080/api/example')
            .then(response => setData(response.data))
            .catch(error => console.error('Error fetching data: ', error));
    }, []);

    return (
        <div>
            <h1>{data}</h1>
        </div>
    );
}

export default App;