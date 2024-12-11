import { useState, useEffect } from "react";

function RecentTransactions( isPolling) {
  const [ticket, setTicket] = useState(0);
  const [transactions, setTransactions] = useState([]);
  const apiUrl = "http://localhost:8080/api/ticketing/latest-transaction";

  useEffect(() => {
    let interval;

    const fetchTransaction = async () => {
      try {
        const response = await fetch(apiUrl);
        if (response.ok) {
          const data = await response.text();
          try{
          const match = data.match(/\b\d+\b/); // Matches the first standalone number

          if (match) {
            const number = parseInt(match[0], 10); // Convert the matched string to an integer
            console.log("Extracted Number:", number);
            setTicket(number);
          } else {
            console.log("No number found in the response.");
          }
        }catch(error){
          console.error("Error parsing transaction:", error);
        }
          // Append the new transaction to the list
          setTransactions((prev) => [data, ...prev].slice(0, 10)); // Keep only the latest 10 transactions
        } else {
          console.error("Failed to fetch transaction");
        }
      } catch (error) {
        console.error("Error fetching transaction:", error);
      }
    };

    if (isPolling) {
      interval = setInterval(fetchTransaction, 1000); // Poll every second
    }

    return () => clearInterval(interval); // Cleanup when component unmounts or polling stops
  }, [isPolling]);

  return (
    <div className="bg-blue-500 rounded-lg p-5 shadow-md text-white">
      <p>New Total Ticket Count: <span className="text-yellow-400">{ticket}</span></p>
      <h2 className="text-lg font-bold mb-4">Recent Transactions</h2>
      <ul className="text-sm space-y-2">
        {transactions.map((transaction, index) => (
          <li key={index} className="border-b border-blue-300 pb-1">
            {transaction}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default RecentTransactions;
