document.addEventListener('DOMContentLoaded', () => {
    let allLogs = []; // Lưu trữ toàn bộ dữ liệu log để lọc trên client-side

    // Hàm lấy dữ liệu log từ backend
    function fetchLogs() {
        fetch('./GetAllLogsController')
            .then(response => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.json();
            })
            .then(logs => {
                allLogs = logs; // Lưu dữ liệu vào biến toàn cục
                console.log(logs);
                renderLogs(logs);
            })
            .catch(error => {
                const tbody = document.querySelector('#logsTable tbody');
                tbody.innerHTML = '<tr><td colspan="8" class="text-center text-danger">Lỗi khi tải dữ liệu log</td></tr>';
                console.error('Fetch error:', error);
            });
    }

    // Hàm render dữ liệu log lên bảng
    function renderLogs(logs) {
        const tbody = document.querySelector('#logsTable tbody');
        tbody.innerHTML = '';

        if (!logs || logs.length === 0) {
            tbody.innerHTML = '<tr><td colspan="8" class="text-center">Không có dữ liệu log</td></tr>';
            return;
        }

        logs.forEach(log => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${log.id ?? ''}</td>
                <td>${log.userId ?? ''}</td>
                <td>${log.action ?? ''}</td>
                <td>${log.tableName ?? ''}</td>
                <td>${log.dataBefore ?? ''}</td>
                <td>${log.dataAfter ?? ''}</td>
                <td>${log.ipAddress ?? ''}</td>
                <td>${log.timestamp ?? ''}</td>
            `;
            tbody.appendChild(row);
        });
    }

    // Hàm lọc log trên client-side
    window.filterLogs = function () {
        const userIdFilter = document.querySelector('#userIdFilter').value.trim().toLowerCase();
        const actionFilter = document.querySelector('#actionFilter').value;
        const tableNameFilter = document.querySelector('#tableNameFilter').value.trim().toLowerCase();
        const dateFilter = document.querySelector('#dateFilter').value;

        const filteredLogs = allLogs.filter(log => {
            const matchesUserId = !userIdFilter || (log.userId && log.userId.toString().toLowerCase().includes(userIdFilter));
            const matchesAction = !actionFilter || (log.action && log.action === actionFilter);
            const matchesTableName = !tableNameFilter || (log.tableName && log.tableName.toLowerCase().includes(tableNameFilter));
            const matchesDate = !dateFilter || (log.timestamp && log.timestamp.startsWith(dateFilter));

            return matchesUserId && matchesAction && matchesTableName && matchesDate;
        });

        renderLogs(filteredLogs);
    };

    // Hàm reset bộ lọc
    window.resetFilter = function () {
        document.querySelector('#userIdFilter').value = '';
        document.querySelector('#actionFilter').value = '';
        document.querySelector('#tableNameFilter').value = '';
        document.querySelector('#dateFilter').value = '';
        renderLogs(allLogs); // Hiển thị lại toàn bộ log
    };

    // Gọi hàm fetchLogs khi trang được tải
    fetchLogs();
});