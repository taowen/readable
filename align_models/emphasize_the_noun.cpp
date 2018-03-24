// bad

void AddStats(const Stats& add_from, Stats* add_to) {
 add_to->set_total_memory(add_from.total_memory() + add_to->total_memory());
 add_to->set_free_memory(add_from.free_memory() + add_to->free_memory());
 add_to->set_swap_memory(add_from.swap_memory() + add_to->swap_memory());
 add_to->set_status_string(add_from.status_string() + add_to->status_string());
 add_to->set_num_processes(add_from.num_processes() + add_to->num_processes());
 ...
}

// good

void AddStats(const Stats& add_from, Stats* add_to) {
 #define ADD_FIELD(field) add_to->set_##field(add_from.field() + add_to->field())
 ADD_FIELD(total_memory);
 ADD_FIELD(free_memory);
 ADD_FIELD(swap_memory);
 ADD_FIELD(status_string);
 ADD_FIELD(num_processes);
 ...
 #undef ADD_FIELD
}
