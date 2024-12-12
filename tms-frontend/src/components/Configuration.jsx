import { useState } from "react";

// eslint-disable-next-line react/prop-types
function Configuration({totalTickets, setTotalTickets}) {
  const [ticketReleaseRate, setTicketReleaseRate] = useState(0);
  const [customerRetrievalRate, setCustomerRetrievalRate] = useState(0);
  const [maxTicketCapacity, setMaxTicketCapacity] = useState(0);

  const handleClick = async () => {
    // Combined validation for 0 values
    if (
      totalTickets <= 0 ||
      ticketReleaseRate <= 0 ||
      customerRetrievalRate <= 0 ||
      maxTicketCapacity <= 0
    ) {
      alert(
        `All fields must have a value greater than 0. Please check the values and try again.`
      );
      return;
    }

    if (totalTickets > maxTicketCapacity) {
      alert(
        `Set Configuration Failed: Total Number of Tickets (${totalTickets}) cannot exceed Maximum Ticket Capacity (${maxTicketCapacity}).`
      );
      return;
    }
  
    const configurationData = {
      totalTickets,
      ticketReleaseRate,
      customerRetrievalRate,
      maxTicketCapacity,
    };
  
    try {
      const response = await fetch("http://localhost:8080/api/ticketing/configure", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(configurationData),
      });
  
      if (response.ok) {
        const message = await response.text();
        alert("Configuration set successfully!" + message);
      } else {
        const error = await response.text();
        alert(`Failed to set configuration: ${error}`);
      }
    } catch (error) {
      alert(`An error occurred: ${error.message}`);
    }
  };
  
  
  return (
    <div className="bg-blue-500 rounded-lg p-5 shadow-md text-white">
      <h2 className="text-lg font-bold mb-4">Configuration</h2>
      <form className="flex flex-col gap-4">
        <label className="flex flex-col">
          Total Number of Tickets:
          <input
            type="number"
            value={totalTickets}
            onChange={(e) => setTotalTickets(Number(e.target.value))}
            className="mt-1 p-2 rounded bg-blue-100 text-blue-900"
          />
        </label>
        <label className="flex flex-col">
          Ticket Release Rate:
          <input
            type="number"
            value={ticketReleaseRate}
            onChange={(e) => setTicketReleaseRate(Number(e.target.value))}
            className="mt-1 p-2 rounded bg-blue-100 text-blue-900"
          />
        </label>
        <label className="flex flex-col">
          Customer Retrieval Rate:
          <input
            type="number"
            value={customerRetrievalRate}
            onChange={(e) => setCustomerRetrievalRate(Number(e.target.value))}
            className="mt-1 p-2 rounded bg-blue-100 text-blue-900"
          />
        </label>
        <label className="flex flex-col">
          Maximum Ticket Capacity:
          <input
            type="number"
            value={maxTicketCapacity}
            onChange={(e) => setMaxTicketCapacity(Number(e.target.value))}
            className="mt-1 p-2 rounded bg-blue-100 text-blue-900"
          />
        </label>
        <button
          type="button"
          onClick={handleClick}
          className="bg-blue-700 hover:bg-blue-800 text-white font-bold py-2 px-4 rounded mt-4"
        >
          Set Configuration
        </button>
      </form>
    </div>
  );
}

export default Configuration;
