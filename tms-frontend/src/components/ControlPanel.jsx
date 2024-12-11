

function ControlPanel(onStart, onStop) {
  const apiUrl = "http://localhost:8080/api/ticketing"; // Backend API URL.

  // Generic handler for POST requests to backend endpoints
  const handleRequest = async (endpoint) => {
    try {
      const response = await fetch(`${apiUrl}/${endpoint}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (response.ok) {
        const message = await response.text();
        alert(message); // Example responses: "System started!", "System stopped!", "System reset!"
      } else {
        const error = await response.text();
        throw new Error(error);
      }
    } catch (error) {
      alert(`Error: ${error.message}`);
    }
  };

  // Handle Start and Stop clicks
  const handleStart = () => {
    handleRequest("start");
    if (onStart) onStart();
  };

  const handleStop = () => {
    handleRequest("stop");
    if (onStop) onStop();
  };

  return (
    <div className="bg-blue-500 rounded-lg p-5 shadow-md text-white">
      <h2 className="text-lg font-bold mb-4">Control Panel</h2>
      <div className="flex gap-4 justify-center">
        {/* Start Button */}
        <button
          className="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded"
          onClick={handleStart}
        >
          Start
        </button>

        {/* Stop Button */}
        <button
          className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded"
          onClick={handleStop}
        >
          Stop
        </button>

        {/* Reset Button */}
        <button
          className="bg-orange-500 hover:bg-orange-600 text-white font-bold py-2 px-4 rounded"
          onClick={() => handleRequest("reset")}
        >
          Reset
        </button>
      </div>
    </div>
  );
}

export default ControlPanel;
