import React, { useEffect, useState } from "react"

const App = () => {
  const [cards, setCards] = useState([])
  const [name, setName] = useState([])
  const [number, setNumber] = useState([])
  const [creditLimit, setCreditLimit] = useState([])
  const [message, setMessage] = useState([])

  const fetchData = () => {
    fetch("http://localhost:8080/cards")
      .then(response => {
        return response.json()
      })
      .then(data => {
        setCards(data)
      })
      .catch((error) => {
        console.log("error=" + error.message)
        setMessage(error)
      })
  }

  const saveCard = () => {
    fetch("http://localhost:8080/cards", {
        method: "POST",
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({name: name, number: number, creditLimit: creditLimit})
      })
      .then(response => {
        console.log("status=" + response.status)
        if (response.status != 200) {
            setMessage("ERROR create card returned " + response.status)
        }
        else {
            setMessage("created new card for " + name)
            fetchData()
        }
      })
      .catch((error) => {
        console.log(error.message)
        setMessage(error)
      })
  }

  useEffect(() => {
    fetchData()
  }, [])

  return (
    <div className="App">
        <h1>Add a Card</h1>
        <p>Name:&nbsp;&nbsp;&nbsp;&nbsp;<input id="name" name="name" type="text" onChange={event => setName(event.target.value)} value={name} /></p>
        <p>Number: <input id="number" name="number" type="text" onChange={event => setNumber(event.target.value)} value={number} /></p>
        <p>Limit:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="limit" name="limit" type="text" onChange={event => setCreditLimit(event.target.value)} value={creditLimit} /></p>
        <p><button onClick={saveCard}>Save Card</button></p>
        <div>{ message ? <p>&nbsp;{message}</p> : null}</div>
        <h1>Existing Cards</h1>
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Number</th>
                    <th>Balance</th>
                    <th>Limit</th>
                </tr>
            </thead>
            <tbody>
              {cards.map(card => (
                <tr key={card.id}>
                    <td>{card.name}</td>
                    <td>{card.number}</td>
                    <td style={{color: card.balance >= 0.0 ? "black" : "red"}}>£{card.balance}</td>
                    <td>£{card.creditLimit}</td>
                </tr>
              ))}
            </tbody>
        </table>
    </div>
  )
}

export default App