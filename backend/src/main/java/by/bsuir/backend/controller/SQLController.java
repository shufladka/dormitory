package by.bsuir.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sql")
public class SQLController extends AbstractController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Method for calling some SQL request
     * @param sql Execute request string
     */
    @PostMapping("/execute")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> executeQuery(@RequestBody String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * Method for getting dormitory table names
     */
    @GetMapping("/tables")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> getTables() {
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = \"dormitory\";";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * Method for getting dormitory foreign keys
     */
    @GetMapping("/foreign-keys")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> getForeignKeys() {
        String sql = "SELECT table_name, column_name, referenced_table_name, referenced_column_name " +
                "FROM information_schema.key_column_usage " +
                "WHERE referenced_table_name IS NOT NULL " +
                "AND table_schema = \"dormitory\";";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * Method for enabling general_log
     */
    @PostMapping("/log/off")
    @ResponseStatus(HttpStatus.OK)
    public void setGeneralLogOff() {
        String sql = "SET GLOBAL general_log = 'OFF';";
        jdbcTemplate.execute(sql);
    }

    /**
     * Method for disabling general_log
     */
    @PostMapping("/log/on")
    @ResponseStatus(HttpStatus.OK)
    public void setGeneralLogOn() {
        String enableLog = "SET GLOBAL general_log = 'ON';";
        String switchOutput = "SET GLOBAL log_output = 'TABLE';";

        jdbcTemplate.execute(switchOutput);
        jdbcTemplate.execute(enableLog);
    }
}
